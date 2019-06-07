package org.dzendzula.restfilterindex.controller;


import org.dzendzula.restfilterindex.controller.vo.DataPreviewDto;
import org.dzendzula.restfilterindex.controller.vo.DbColumnInfoDto;
import org.dzendzula.restfilterindex.service.DbViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class DbViewerController {

    @Autowired
    private DbViewerService viewerService;

    @RequestMapping(value = "/{id}/schema/", method = RequestMethod.GET)
    public List<String> getSchemas(@PathVariable("id") Long connectionSettingsId) {
        return viewerService.fetchSchemas(connectionSettingsId);
    }


    @RequestMapping(value = "/{id}/tables/", method = RequestMethod.GET)
    public List<String> getTables(@PathVariable("id") Long connectionSettingsId, @RequestParam("schemaName") String schemaName) {
        return viewerService.fetchTables(connectionSettingsId, schemaName);
    }

    @RequestMapping(value = "/{id}/columns", method = RequestMethod.GET)
    public List<DbColumnInfoDto> getColumns(@PathVariable("id") Long connectionSettingsId, @RequestParam("schemaName") String schemaName, @RequestParam("tableName") String tableName) {
        return viewerService.fetchColumns(connectionSettingsId, schemaName, tableName);
    }

    @RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
    public DataPreviewDto getData(@PathVariable("id") Long connectionSettingsId, @RequestParam("schemaName") String schemaName, @RequestParam("tableName") String tableName) {
        return viewerService.fetchData(connectionSettingsId, schemaName, tableName);
    }


}
