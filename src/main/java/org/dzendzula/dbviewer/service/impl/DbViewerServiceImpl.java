package org.dzendzula.dbviewer.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dzendzula.dbviewer.connector.DbConnectionManager;
import org.dzendzula.dbviewer.controller.dto.DataPreviewDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnInfoDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnStatisticsDto;
import org.dzendzula.dbviewer.controller.dto.DbTableStatisticsDto;
import org.dzendzula.dbviewer.controller.exceptions.ConnectionValidationException;
import org.dzendzula.dbviewer.controller.exceptions.SQLRequestException;
import org.dzendzula.dbviewer.service.DbViewerService;
import org.dzendzula.dbviewer.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class DbViewerServiceImpl implements DbViewerService {

    private static final String[] TABLE_TYPES = {"TABLE"};

    private Logger logger = LoggerFactory.getLogger(DbConnectionSettingsServiceImpl.class);

    @Autowired
    private DbConnectionManager dbConnectionManager;

    @Override
    public List<String> fetchSchemas(Long id) {
        List<String> result = new ArrayList<>();

        processDbMetadata(id, (meta) -> {
            try {
                ResultSet resultSet = meta.getSchemas();
                while (resultSet.next()) {
                    result.add(resultSet.getString(Constants.JDBC_SCHEMA_INFO_COLUMNAME));
                }
            } catch (SQLException e) {
                logger.error("Error while fetching database schemas. " + e.getMessage());
                throw new SQLRequestException("schema", e.getMessage(), id.toString());
            }
        });

        return result;

    }

    @Override
    public List<String> fetchTables(Long connectionSettingsId, String schemaName) {
        List<String> result = new ArrayList<>();
        processDbMetadata(connectionSettingsId, (meta) -> {
            try {
                ResultSet resultSet = meta.getTables(null, schemaName, null, TABLE_TYPES);
                while (resultSet.next()) {
                    result.add(resultSet.getString(Constants.JDBC_TABLE_INFO_COLUMNAME));
                }
            } catch (SQLException e) {
                logger.error("Error while fetching tables for schema [" + schemaName + "]. " + e.getMessage());
                throw new SQLRequestException("tables", e.getMessage(), connectionSettingsId.toString());
            }
        });
        return result;
    }

    @Override
    public List<DbColumnInfoDto> fetchColumns(Long connectionSettingsId, String schemaName, String tableName) {
        List<DbColumnInfoDto> result = new ArrayList<>();
        processDbMetadata(connectionSettingsId, (meta) -> {
            try {
                List<String> pkNames = new ArrayList<>();
                ResultSet resultPKSet = meta.getPrimaryKeys(null, schemaName, tableName);
                // getImportedKeys
                // getExportedKeys
                while (resultPKSet.next()) {
                    pkNames.add(resultPKSet.getString(Constants.JDBC_PK_INFO_COLUMNAME));
                }
                ResultSet resultSet = meta.getColumns(null, schemaName, tableName, null);
                while (resultSet.next()) {
                    DbColumnInfoDto dto = new DbColumnInfoDto();
                    dto.setName(resultSet.getString(Constants.JDBC_COLUMN_INFO_NAME));
                    dto.setType(resultSet.getString(Constants.JDBC_COLUMN_INFO_DATA_TYPE));
                    dto.setTypeName(resultSet.getString(Constants.JDBC_COLUMN_INFO_TYPE_NAME));
                    dto.setColumnSize(resultSet.getString(Constants.JDBC_COLUMN_INFO_COLUMN_SIZE));
                    dto.setDecimalDigits(resultSet.getString(Constants.JDBC_COLUMN_INFO_DECIMAL_DIGITS));
                    dto.setNumPrecRadix(resultSet.getString(Constants.JDBC_COLUMN_INFO_NUM_PREC_RADIX));
                    dto.setRemarks(resultSet.getString(Constants.JDBC_COLUMN_INFO_REMARKS));
                    dto.setColumnDefaultValue(resultSet.getString(Constants.JDBC_COLUMN_INFO_COLUMN_DEF));
                    dto.setCharOctetLength(resultSet.getString(Constants.JDBC_COLUMN_INFO_CHAR_OCTET_LENGTH));
                    dto.setOrdinalPosition(resultSet.getString(Constants.JDBC_COLUMN_INFO_ORDINAL_POSITION));
                    dto.setIsNullable(resultSet.getString(Constants.JDBC_COLUMN_INFO_IS_NULLABLE));
                    dto.setIsAutoincrement(resultSet.getString(Constants.JDBC_COLUMN_INFO_IS_AUTOINCREMENT));
                    dto.setPrimaryKey(pkNames.contains(dto.getName()));
                    result.add(dto);
                }
            } catch (SQLException e) {
                logger.error("Error while fetching columns for table [" + schemaName + "." + tableName + "]. " + e.getMessage());
                throw new SQLRequestException("columns", e.getMessage(), connectionSettingsId.toString());
            }
        });
        return result;
    }


    @Override
    public DataPreviewDto fetchData(Long connectionSettingsId, String schemaName, String tableName) {
        DataPreviewDto result = new DataPreviewDto();
        List<String> columnNames = new ArrayList<>();
        List<HashMap<String, String>> data = new ArrayList<>();
        Connection connection = dbConnectionManager.getConnection(connectionSettingsId);
        if (connection == null) {
            logger.debug("Cannot fetch data. Conection cannot be established");
            throw new ConnectionValidationException(connectionSettingsId.toString());
        }
        try {
            Statement stmt = connection.createStatement();
            //TODO make pageable from rest
            String query = Constants.SQL_SELECT_ALL_FROM + tableName + " LIMIT " + Constants.PAGE_DAFAULT_LIMIT;

            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            int rowCount = 0;
            while (rs.next()) {
                final HashMap<String, String> row = new HashMap<>();
                columnNames.forEach(colName -> {
                    try {
                        row.put(colName, rs.getString(colName));
                    } catch (SQLException e) {
                        logger.error("Error while fetching data from table column[" + schemaName + "." + tableName + "." + colName + "]. " + e.getMessage());
                        throw new SQLRequestException("dataPreview", e.getMessage(), connectionSettingsId.toString());
                    }
                });
                data.add(row);
                rowCount = rs.getRow();
            }
            result.setColumns(columnNames);
            result.setData(data);
            result.setRowCount(rowCount);

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error while fetching data from table [" + schemaName + "." + tableName + "]. " + e.getMessage());
            throw new SQLRequestException("dataPreview", e.getMessage(), connectionSettingsId.toString());
        }
        return result;
    }


    @Override
    public DbTableStatisticsDto fetchTableStatistics(Long connectionSettingsId, String schemaName, String tableName) {
        DbTableStatisticsDto result = new DbTableStatisticsDto();
        result.setName(tableName);
        Connection connection = dbConnectionManager.getConnection(connectionSettingsId);
        if (connection == null) {
            logger.debug("Cannot fetch data. Conection cannot be established");
            throw new ConnectionValidationException(connectionSettingsId.toString());
        }
        try {
            DatabaseMetaData meta = connection.getMetaData();

            ResultSet columnsRs = meta.getColumns(null, schemaName, tableName, null);
            columnsRs.last();
            result.setNumberOfAttributes(columnsRs.getRow());

            Statement stmt = connection.createStatement();
            String query = "SELECT COUNT(*) FROM " + tableName;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            result.setNumberOfRecords(rs.getInt(1));

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error while fetching data from table [" + schemaName + "." + tableName + "]. " + e.getMessage());
            throw new SQLRequestException("statistics", e.getMessage(), connectionSettingsId.toString());
        }

        return result;
    }

    @Override
    public List<DbColumnStatisticsDto> fetchColumnStatistics(Long connectionSettingsId, String schemaName, String tableName) {
        List<DbColumnStatisticsDto> result = new ArrayList<>();
        Connection connection = dbConnectionManager.getConnection(connectionSettingsId);
        if (connection == null) {
            logger.debug("Cannot fetch data. Conection cannot be established");
            throw new ConnectionValidationException(connectionSettingsId.toString());
        }
        try {
            HashMap<String, Boolean> columnNames = new HashMap<>();
            List<String> queryRsList = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, schemaName, tableName, null);
            while (resultSet.next()) {
                String colName = resultSet.getString(Constants.JDBC_COLUMN_INFO_NAME);
                queryRsList.add("min(" + colName + ") as " + colName + "_max");
                queryRsList.add("max(" + colName + ") as " + colName + "_min");
                queryRsList.add("percentile_disc(0.5) within group (order by " + colName + ") as " + colName + "_med");
                boolean isNumeric = false;
                if (resultSet.getInt(Constants.JDBC_COLUMN_INFO_DATA_TYPE) >= 2 && resultSet.getInt(Constants.JDBC_COLUMN_INFO_DATA_TYPE) <= 8) {
                    queryRsList.add("avg(" + colName + ") as " + colName + "_avg");
                    isNumeric = true;
                }
                columnNames.put(colName, isNumeric);
            }

            Statement stmt = connection.createStatement();
            String query = "SELECT " + StringUtils.join(queryRsList, ",") + " FROM " + tableName;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                for (Map.Entry<String, Boolean> column : columnNames.entrySet()) {
                    DbColumnStatisticsDto dto = new DbColumnStatisticsDto();
                    String name = column.getKey();
                    dto.setName(name);
                    dto.setMin(rs.getString(name + "_max"));
                    dto.setMax(rs.getString(name + "_min"));
                    dto.setMed(rs.getString(name + "_med"));
                    if (column.getValue()) {
                        dto.setAvg(rs.getString(name + "_avg"));
                    }
                    result.add(dto);
                }
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error while fetching data from table [" + schemaName + "." + tableName + "]. " + e.getMessage());
            throw new SQLRequestException("dataPreview", e.getMessage(), connectionSettingsId.toString());
        }

        return result;
    }

    private void processDbMetadata(Long id, Consumer<DatabaseMetaData> consumer) {
        Connection connection = dbConnectionManager.getConnection(id);
        if (connection == null) {
            logger.debug("Cannot fetch metada. Conection cannot be established");
            return;
        }
        try {
            DatabaseMetaData meta = connection.getMetaData();
            consumer.accept(meta);
            connection.close();
        } catch (SQLException e) {
            logger.error("Error while acquiring database metadata for connection settings with ID =[" + id + "]. " + e.getMessage());
            throw new SQLRequestException("schema", e.getMessage(), id.toString());
        }

    }

}
