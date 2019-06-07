package org.dzendzula.restfilterindex.service.impl;

import org.dzendzula.restfilterindex.connector.DbConnectionPool;
import org.dzendzula.restfilterindex.controller.vo.DataPreviewDto;
import org.dzendzula.restfilterindex.controller.vo.DbColumnInfoDto;
import org.dzendzula.restfilterindex.service.DbViewerService;
import org.dzendzula.restfilterindex.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Service
public class DbViewerServiceImpl implements DbViewerService {

    private static final String[] TABLE_TYPES = {"TABLE"};
    private Logger logger = LoggerFactory.getLogger(DbConnectionSettingsServiceImpl.class);

    @Autowired
    private DbConnectionPool dbConnectionPool;

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
                logger.error("Error while fetching database tables for schema [" + schemaName + "]. " + e.getMessage());
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
                logger.error("Error while fetching database tables for schema [" + schemaName + "]. " + e.getMessage());
            }
        });
        return result;
    }


    @Override
    public DataPreviewDto fetchData(Long connectionSettingsId, String schemaName, String tableName) {
        DataPreviewDto result = new DataPreviewDto();
        List<String> columnNames = new ArrayList<>();
        List<HashMap<String, String>> data = new ArrayList<>();
        Connection connection = dbConnectionPool.getConnection(connectionSettingsId);
        try {
            Statement stmt = connection.createStatement();
            String query = Constants.SQL_SELECT_ALL_FROM + tableName;

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
                        e.printStackTrace();
                    }
                });
                data.add(row);
                rowCount = rs.getRow();
            }
            stmt.close();
            connection.close();
            result.setColumns(columnNames);
            result.setData(data);
            result.setRowCount(rowCount);
        } catch (SQLException e) {
            logger.error("Error while fetching data from table [" + schemaName + "." + tableName + "]. " + e.getMessage());
        }
        return result;
    }

    private void processDbMetadata(Long id, Consumer<DatabaseMetaData> consumer) {
        Connection connection = dbConnectionPool.getConnection(id);
        try {
            DatabaseMetaData meta = connection.getMetaData();
            consumer.accept(meta);
            connection.close();
        } catch (SQLException e) {
            logger.error("Error while acquiring database metadata for connection settings with ID =[" + id + "]. " + e.getMessage());
        }

    }

}
