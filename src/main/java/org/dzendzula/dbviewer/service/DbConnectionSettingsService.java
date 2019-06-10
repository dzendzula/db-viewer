package org.dzendzula.dbviewer.service;


import org.dzendzula.dbviewer.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface DbConnectionSettingsService {

    List<DbConnectionSettingsDto> findAllDbConnectionSettings();

    DbConnectionSettingsDto findDbConnectionSettings(Long id);

    DbConnectionSettingsBo getDbConnectionSettings(Long id);

    DbConnectionSettingsDto createDbConnectionSettings(@NonNull DbConnectionSettingsDto dto);

    DbConnectionSettingsDto updateDbConnectionSettings(@NonNull Long id, @NonNull DbConnectionSettingsDto dto);

    void deleteDbConnectionSettings(Long dto);
}
