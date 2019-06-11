package org.dzendzula.dbviewer.controller;


import io.swagger.annotations.*;
import org.dzendzula.dbviewer.controller.dto.DataPreviewDto;
import org.dzendzula.dbviewer.controller.dto.DbColumnInfoDto;
import org.dzendzula.dbviewer.service.DbViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "DB viewer API")
@RestController
@RequestMapping(value = "/")
public class DbViewerController {

    @Autowired
    private DbViewerService viewerService;

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of schema names present in a specific DB",
            response = String[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schema list"),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/schema/", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getSchemas(@ApiParam @PathVariable("id") Long connectionSettingsId) {
        return new ResponseEntity<>(viewerService.fetchSchemas(connectionSettingsId), HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of table names present in a specific DB schema",
            response = String[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Table list"),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/tables/", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getTables(@ApiParam @PathVariable("id") Long connectionSettingsId,
                                                  @ApiParam @RequestParam("schemaName") String schemaName) {
        return new ResponseEntity<>(viewerService.fetchTables(connectionSettingsId, schemaName), HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of column names present in a specific DB table",
            response = DbColumnInfoDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Columns listed"),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/columns", method = RequestMethod.GET)
    public ResponseEntity<List<DbColumnInfoDto>> getColumns(
            @ApiParam @PathVariable("id") Long connectionSettingsId,
            @ApiParam @RequestParam("schemaName") String schemaName,
            @ApiParam @RequestParam("tableName") String tableName) {
        return new ResponseEntity<>(viewerService.fetchColumns(connectionSettingsId, schemaName, tableName), HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to view data persisted in a specific DB table",
            response = DataPreviewDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Data listed"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Error while requesting DB."),
            @ApiResponse(code = 503, message = "Connection to viewed database failed.")
    })
    @RequestMapping(value = "/{id}/data", method = RequestMethod.GET)
    public ResponseEntity<DataPreviewDto> getData(
            @ApiParam @PathVariable("id") Long connectionSettingsId,
            @ApiParam @RequestParam("schemaName") String schemaName,
            @ApiParam @RequestParam("tableName") String tableName) {
        DataPreviewDto dataPreviewDto = viewerService.fetchData(connectionSettingsId, schemaName, tableName);
        return new ResponseEntity<>(dataPreviewDto, HttpStatus.OK);
    }


}
