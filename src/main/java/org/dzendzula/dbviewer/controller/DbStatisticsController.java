package org.dzendzula.dbviewer.controller;


import io.swagger.annotations.*;
import org.dzendzula.dbviewer.controller.dto.DbColumnInfoDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnStatisticsDto;
import org.dzendzula.dbviewer.controller.dto.DbTableStatisticsDto;
import org.dzendzula.dbviewer.service.DbViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "DB viewer API")
@RestController
@RequestMapping(value = "/statistics")
public class DbStatisticsController {

    @Autowired
    private DbViewerService viewerService;


    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a table statistics",
            response = String[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Table list"),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/tables/", method = RequestMethod.GET)
    public ResponseEntity<DbTableStatisticsDto> getTableStatisticss(@ApiParam @PathVariable("id") Long connectionSettingsId,
                                                                    @ApiParam @RequestParam("schemaName") String schemaName,
                                                                    @ApiParam @RequestParam("tableName") String tableName) {
        return new ResponseEntity<>(viewerService.fetchTableStatistics(connectionSettingsId, schemaName, tableName), HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of column names present in a specific DB table",
            response = DbColumnInfoDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Columns listed"),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/columns", method = RequestMethod.GET)
    public ResponseEntity<List<DbColumnStatisticsDto>> getColumns(
            @ApiParam @PathVariable("id") Long connectionSettingsId,
            @ApiParam @RequestParam("schemaName") String schemaName,
            @ApiParam @RequestParam("tableName") String tableName) {
        return new ResponseEntity<>(viewerService.fetchColumnStatistics(connectionSettingsId, schemaName, tableName), HttpStatus.OK);
    }


}
