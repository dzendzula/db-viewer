package org.dzendzula.dbviewer.service;

import org.dzendzula.dbviewer.controller.dto.DataPreviewDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnInfoDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnStatisticsDto;
import org.dzendzula.dbviewer.controller.dto.DbTableStatisticsDto;

import java.util.List;

public interface DbViewerService {

    /**
     * Returns a list of avaliable schemas in a database specified by db connection settings ID
     *
     * @param connectionSettingsId ID of connection settings
     * @return
     */
    List<String> fetchSchemas(Long connectionSettingsId);

    /**
     * Returns a list of avaliable tables in schema of the database specified by db connection settings ID
     *
     * @param connectionSettingsId ID of connection settings
     * @param schemaName           name of schema to be explored
     * @return
     */
    List<String> fetchTables(Long connectionSettingsId, String schemaName);

    /**
     * Returns a list of avaliable columns in a table of the database specified by db connection settings ID
     * @param connectionSettingsId ID of connection settings
     * @param schemaName name of schema to be explored
     * @param tableName name of table to be explored
     * @return
     */
    List<DbColumnInfoDto> fetchColumns(Long connectionSettingsId, String schemaName, String tableName);

    /**
     * Returns data preview object containing data of a specific table.
     * @param connectionSettingsId ID of connection settings
     * @param schemaName name of schema to be explored
     * @param tableName name of table to be explored
     * @return
     */
    DataPreviewDto fetchData(Long connectionSettingsId, String schemaName, String tableName);

    /**
     * Returns a statistic object for a specified table
     *
     * @param connectionSettingsId ID of connection settings
     * @param schemaName           name of schema to be explored
     * @param tableName            name of table to be explored
     * @return
     */
    DbTableStatisticsDto fetchTableStatistics(Long connectionSettingsId, String schemaName, String tableName);

    /**
     * Returns a statistic object for a specified table columns
     *
     * @param connectionSettingsId ID of connection settings
     * @param schemaName           name of schema to be explored
     * @param tableName            name of table to be explored
     * @return
     */
    List<DbColumnStatisticsDto> fetchColumnStatistics(Long connectionSettingsId, String schemaName, String tableName);
}
