package org.dzendzula.dbviewer.service;


import org.dzendzula.dbviewer.controller.dto.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DbConnectionSettingsService {

    /**
     * Methods finds all stored connectiopn settings
     *
     * @return List<@ DbConnectionSettingsDto>
     */
    List<DbConnectionSettingsDto> findAllDbConnectionSettings();

    /**
     * Methods finds stored connectiopn settings by given ID
     * @param id id of setting
     * @return @DbConnectionSettingsDto
     */
    DbConnectionSettingsDto findDbConnectionSettings(Long id);

    /**
     * Returns a persistent BO of connection setting by ID
     * @param id id of settings
     * @return
     */
    DbConnectionSettingsBo getDbConnectionSettings(Long id);

    /**
     * Creates and stores new db connection settings object
     * @param dto
     * @return
     */
    DbConnectionSettingsDto createDbConnectionSettings(@NonNull DbConnectionSettingsDto dto);

    /**
     * Updates and stores new db connection settings object
     * @param id ID of settings to be updated
     * @param dto
     * @return
     */
    DbConnectionSettingsDto updateDbConnectionSettings(@NonNull Long id, @NonNull DbConnectionSettingsDto dto);

    /**
     * Deletes a specific db settings config
     *
     * @param id ID of config to be deleted
     */
    void deleteDbConnectionSettings(Long id);
}
