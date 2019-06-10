package org.dzendzula.dbviewer.converter;


import org.dzendzula.dbviewer.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.springframework.stereotype.Component;

/**
 * Convertor class for purpose of converting @{@link DbConnectionSettingsBo} business object to @{@link DbConnectionSettingsDto} data transfer object.
 * All validations, data manipulation for presentation purposes should be applied here
 */
@Component
public class DbConnectionSettingsConverter implements ConverterReversable<DbConnectionSettingsBo, DbConnectionSettingsDto> {

    @Override
    public DbConnectionSettingsDto convert(DbConnectionSettingsBo source) {
        if (source == null) {
            return null;
        }
        DbConnectionSettingsDto dto = new DbConnectionSettingsDto();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setHost(source.getHost());
        dto.setPort(source.getPort());
        dto.setDbName(source.getDbName());
        dto.setUsername(source.getUsername());
        dto.setPassword(source.getPassword());
        return dto;

    }

    @Override
    public DbConnectionSettingsBo convertReverse(DbConnectionSettingsDto dto) {
        if (dto == null) {
            return null;
        }
        DbConnectionSettingsBo settingsBo = new DbConnectionSettingsBo();
        settingsBo.setName(dto.getName());
        settingsBo.setHost(dto.getHost());
        settingsBo.setPort(dto.getPort());
        settingsBo.setDbName(dto.getDbName());
        settingsBo.setUsername(dto.getUsername());
        settingsBo.setPassword(dto.getPassword());
        return settingsBo;
    }
}
