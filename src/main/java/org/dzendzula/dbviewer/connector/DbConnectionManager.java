package org.dzendzula.dbviewer.connector;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dzendzula.dbviewer.controller.dto.DbConnectionSettingsDto;
import org.dzendzula.dbviewer.controller.exceptions.ConnectionValidationException;
import org.dzendzula.dbviewer.controller.exceptions.NotFoundValidationException;
import org.dzendzula.dbviewer.domain.DbConnectionSettingsBo;
import org.dzendzula.dbviewer.service.DbConnectionSettingsService;
import org.dzendzula.dbviewer.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Service
public class DbConnectionManager {

    private static final HashMap<Long, HikariDataSource> configs = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(DbConnectionManager.class);

    @Autowired
    private DbConnectionSettingsService settingsService;

    public Connection getConnection(Long settingsId) {
        if (settingsId == null || settingsId <= 0L) {
            return null;
        }
        try {
            HikariDataSource ds = configs.get(settingsId);
            if (ds != null) {
                // already loaded configuration
                return ds.getConnection();
            } else {
                //load configuration to map and return connection
                return addConfig(settingsId).getConnection();

            }
        } catch (SQLException e) {
            logger.error("Connection cannot be established. " + e.getMessage());
            throw new ConnectionValidationException(settingsId.toString(), settingsId.toString());
        }
    }

    private HikariDataSource addConfig(Long settingsId) {
        DbConnectionSettingsDto settings = settingsService.findDbConnectionSettings(settingsId);
        if (settings == null) {
            logger.error("Initializaton of DB connetion with settings ID = [" + settingsId + "] failed. Setting were not found");
            throw new NotFoundValidationException(DbConnectionSettingsBo.class, settings.toString());
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(formJdbcUrl(settings));
        config.setUsername(settings.getUsername());
        config.setPassword(settings.getPassword());
        try {
            HikariDataSource dataSource = new HikariDataSource(config);
            configs.put(settings.getId(), dataSource);
            return dataSource;
        } catch (Exception e) {
            logger.error("Initializaton of DB connetion with settings ID = [" + settings.getId() + "] failed. " + e.getMessage());
            throw new ConnectionValidationException(settings.getName(), settingsId.toString());
        }
    }

    private String formJdbcUrl(DbConnectionSettingsDto settings) {
        if (settings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(Constants.JDBC_POSTGRESQL_PREFIX);
        sb.append(settings.getHost());
        sb.append(":");
        sb.append(settings.getPort().toString());
        sb.append("/");
        sb.append(settings.getDbName());
        return sb.toString();
    }

}
