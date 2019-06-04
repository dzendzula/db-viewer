package org.dzendzula.restfilterindex.controller;


import org.dzendzula.restfilterindex.controller.filter.DbConnectionFilterDto;
import org.dzendzula.restfilterindex.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.restfilterindex.service.DbConnectionSettingsService;
import org.dzendzula.restfilterindex.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = Constants.DB_SETTINGS_CONTROLLER_MAPPING)
public class DbConnectionSettingsController {

    @Autowired
    private DbConnectionSettingsService settingsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<DbConnectionSettingsDto> getConnections() {
        return settingsService.findAllDbConnectionSettings();
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<DbConnectionSettingsDto> getFilteredConnections(@RequestBody @Validated DbConnectionFilterDto filter) {
        return settingsService.findDbConnectionSettings(filter);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DbConnectionSettingsDto findConnections(@PathVariable("id") Long id) {
        return settingsService.findDbConnectionSettings(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public DbConnectionSettingsDto createConnections(@RequestBody @Valid DbConnectionSettingsDto dto) {
        return settingsService.createDbConnectionSettings(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DbConnectionSettingsDto updateConnections(@PathVariable(value = "id", required = true) Long id, @RequestBody @Valid DbConnectionSettingsDto dto) {
        return settingsService.updateDbConnectionSettings(id, dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteConnections(@PathVariable(value = "id", required = true) Long id) {
        settingsService.deleteDbConnectionSettings(id);
    }


}
