package org.dzendzula.restfilterindex.service.impl;


import org.dzendzula.restfilterindex.controller.filter.DbConnectionFilterDto;
import org.dzendzula.restfilterindex.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.restfilterindex.converter.DbConnectionSettingsConverter;
import org.dzendzula.restfilterindex.domain.DbConnectionSettingsBo;
import org.dzendzula.restfilterindex.repository.DbConnectionSettingsRepository;
import org.dzendzula.restfilterindex.service.DbConnectionSettingsService;
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

    Logger logger = LoggerFactory.getLogger(DbConnectionSettingsServiceImpl.class);

    @Autowired
    private DbConnectionSettingsRepository settingsRepository;
    @Autowired
    private DbConnectionSettingsConverter settingsConverter;

    @Override
    public List<DbConnectionSettingsDto> findAllDbConnectionSettings() {
        return settingsRepository.findAll().stream().map(settingsConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<DbConnectionSettingsDto> findDbConnectionSettings(DbConnectionFilterDto filter) {
        return null;
    }

    @Override
    public DbConnectionSettingsDto findDbConnectionSettings(@NonNull Long id) {
        if (id == null || id <= 0L) {
            logger.error("ID value sholud not bu null or less than zero");
            return null;
        }
        Optional<DbConnectionSettingsBo> settingsBo = settingsRepository.findById(id);
        return settingsBo.map(dbConnectionSettingsBo -> settingsConverter.convert(dbConnectionSettingsBo)).orElse(null);
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
            return null;
        }
        Optional<DbConnectionSettingsBo> settingsBoOptional = settingsRepository.findById(id);

        return settingsBoOptional.map(dbConnectionSettingsBo -> {
            DbConnectionSettingsBo settingsBo = settingsConverter.convertReverse(dto);
            settingsBo.setId(id);
            settingsRepository.save(settingsBo);
            return settingsConverter.convert(settingsBo);
        }).orElse(null);

    }

    @Override
    @Transactional
    public void deleteDbConnectionSettings(Long id) {
        if (id == null || id <= 0L) {
            logger.error("ID value sholud not bu null or less than zero");
            return;
        }
        settingsRepository.deleteById(id);
    }
}
