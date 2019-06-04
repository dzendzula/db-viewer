package org.dzendzula.restfilterindex.service;


import org.dzendzula.restfilterindex.controller.filter.DbConnectionFilterDto;
import org.dzendzula.restfilterindex.controller.vo.DbConnectionSettingsDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface DbConnectionSettingsService {

    List<DbConnectionSettingsDto> findAllDbConnectionSettings();

    List<DbConnectionSettingsDto> findDbConnectionSettings(@Nullable DbConnectionFilterDto filter);

    DbConnectionSettingsDto findDbConnectionSettings(Long id);

    DbConnectionSettingsDto createDbConnectionSettings(@NonNull DbConnectionSettingsDto dto);

    DbConnectionSettingsDto updateDbConnectionSettings(@NonNull Long id, @NonNull DbConnectionSettingsDto dto);

    void deleteDbConnectionSettings(Long dto);
}
