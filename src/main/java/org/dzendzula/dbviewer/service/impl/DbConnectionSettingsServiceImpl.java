package org.dzendzula.dbviewer.service.impl;


import org.dzendzula.dbviewer.controller.dto.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.controller.exceptions.BadRequestValidationException;
import org.dzendzula.dbviewer.controller.exceptions.NotFoundValidationException;
import org.dzendzula.dbviewer.controller.exceptions.ParamSource;
import org.dzendzula.dbviewer.converter.DbConnectionSettingsConverter;
import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.dzendzula.dbviewer.repository.DbConnectionSettingsRepository;
import org.dzendzula.dbviewer.service.DbConnectionSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DbConnectionSettingsServiceImpl implements DbConnectionSettingsService {

    private Logger logger = LoggerFactory.getLogger(DbConnectionSettingsServiceImpl.class);

    @Autowired
    private DbConnectionSettingsRepository settingsRepository;
    @Autowired
    private DbConnectionSettingsConverter settingsConverter;

    @Override
    public List<DbConnectionSettingsDto> findAllDbConnectionSettings() {
        return settingsRepository.findAll().stream().map(settingsConverter::convert).collect(Collectors.toList());
    }

    @Override
    public DbConnectionSettingsBo getDbConnectionSettings(Long id) {
        return settingsRepository.findById(id).orElse(null);
    }

    @Override
    public DbConnectionSettingsDto findDbConnectionSettings(@NonNull Long id) {
        if (id == null || id <= 0L) {
            logger.error("ID value sholud not bu null or less than zero");
            throw BadRequestValidationException.toFieldError("Bad Id.", ParamSource.PATH, "settingsId", id);
        }
        Optional<DbConnectionSettingsBo> settingsBo = settingsRepository.findById(id);
        return settingsBo.map(dbConnectionSettingsBo -> settingsConverter.convert(dbConnectionSettingsBo)).orElseThrow(() -> new NotFoundValidationException(DbConnectionSettingsBo.class, id.toString()));
    }

    @Override
    @Transactional
    public DbConnectionSettingsDto createDbConnectionSettings(@NonNull DbConnectionSettingsDto dto) {
        DbConnectionSettingsBo bo = settingsRepository.save(settingsConverter.convertReverse(dto));
        return settingsConverter.convert(bo);
    }

    @Override
    @Transactional
    public DbConnectionSettingsDto updateDbConnectionSettings(Long id, DbConnectionSettingsDto dto) {
        if (id == null || id <= 0L) {
            logger.error("ID value sholud not bu null or less than zero");
            throw BadRequestValidationException.toFieldError("Bad Id.", ParamSource.PATH, "settingsId", id);
        }
        Optional<DbConnectionSettingsBo> settingsBoOptional = settingsRepository.findById(id);

        return settingsBoOptional.map(dbConnectionSettingsBo -> {
            DbConnectionSettingsBo settingsBo = settingsConverter.convertReverse(dto);
            settingsBo.setId(id);
            settingsRepository.save(settingsBo);
            return settingsConverter.convert(settingsBo);
        }).orElseThrow(() -> new NotFoundValidationException(DbConnectionSettingsBo.class, id.toString()));

    }

    @Override
    @Transactional
    public void deleteDbConnectionSettings(Long id) {
        if (id == null || id <= 0L) {
            logger.error("ID value sholud not bu null or less than zero");
            throw BadRequestValidationException.toFieldError("Bad Id.", ParamSource.PATH, "settingsId", id);
        }
        settingsRepository.deleteById(id);
    }
}
