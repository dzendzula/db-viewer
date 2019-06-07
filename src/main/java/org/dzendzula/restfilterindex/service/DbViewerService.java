package org.dzendzula.restfilterindex.service;

import org.dzendzula.restfilterindex.controller.vo.DataPreviewDto;
import org.dzendzula.restfilterindex.controller.vo.DbColumnInfoDto;

import java.util.List;

public interface DbViewerService {

    List<String> fetchSchemas(Long id);

    List<String> fetchTables(Long connectionSettingsId, String schemaName);

    List<DbColumnInfoDto> fetchColumns(Long connectionSettingsId, String schemaName, String tableName);

    DataPreviewDto fetchData(Long connectionSettingsId, String schemaName, String tableName);
}
