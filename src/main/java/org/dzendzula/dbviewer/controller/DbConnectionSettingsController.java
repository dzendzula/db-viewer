package org.dzendzula.dbviewer.controller;


import io.swagger.annotations.*;
import org.dzendzula.dbviewer.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.service.DbConnectionSettingsService;
import org.dzendzula.dbviewer.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "DB connection settings API")
@RestController
@RequestMapping(value = Constants.DB_SETTINGS_CONTROLLER_MAPPING)
public class DbConnectionSettingsController {

    @Autowired
    private DbConnectionSettingsService settingsService;

    @ApiOperation(httpMethod = "GET",
            value = "Resource to get a list of known DB connection settings.",
            response = DbConnectionSettingsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "DB connection settings list")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<DbConnectionSettingsDto> getConnections() {
        return settingsService.findAllDbConnectionSettings();
    }

    @ApiOperation(httpMethod = "GET",
            value = "Resource to DB connection settings specified by ID.",
            response = DbConnectionSettingsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "DB connection settings"),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 404, message = "DB connection settings not found.")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DbConnectionSettingsDto> findConnections(@ApiParam @PathVariable("id") Long id) {
        DbConnectionSettingsDto settingsDto = settingsService.findDbConnectionSettings(id);
        return new ResponseEntity<>(settingsDto, HttpStatus.OK);

    }

    @ApiOperation(httpMethod = "POST",
            value = "Resource to create DB connection settings.",
            response = DbConnectionSettingsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Created DB connection settings"),
            @ApiResponse(code = 400, message = "Bad request.")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<DbConnectionSettingsDto> createConnections(@ApiParam @RequestBody @Valid DbConnectionSettingsDto dto) {
        DbConnectionSettingsDto settingsDto = settingsService.createDbConnectionSettings(dto);
        return new ResponseEntity<>(settingsDto, HttpStatus.OK);

    }

    @ApiOperation(httpMethod = "PUT",
            value = "Resource to update DB connection settings.",
            response = DbConnectionSettingsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated DB connection settings"),
            @ApiResponse(code = 400, message = "Bad request params"),
            @ApiResponse(code = 404, message = "DB connection settings to update  were not found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DbConnectionSettingsDto> updateConnections(@ApiParam @PathVariable(value = "id", required = true) Long id, @RequestBody @Valid DbConnectionSettingsDto dto) {
        DbConnectionSettingsDto settingsDto = settingsService.updateDbConnectionSettings(id, dto);
        return new ResponseEntity<>(settingsDto, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE",
            value = "Resource to delete DB connection settings.",
            response = DbConnectionSettingsDto[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "DB connection settings were deleted"),
            @ApiResponse(code = 404, message = "DB connection settings to delete was not found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteConnections(@PathVariable(value = "id", required = true) Long id) {
        settingsService.deleteDbConnectionSettings(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
