package org.dzendzula.restfilterindex.connector;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dzendzula.restfilterindex.controller.vo.DbConnectionSettingsDto;
import org.dzendzula.restfilterindex.service.DbConnectionSettingsService;
import org.dzendzula.restfilterindex.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
                return ds.getConnection();
            }
        } catch (SQLException e) {
            logger.error("Connection cannot be established. " + e.getMessage());
        }
        return null;
    }

    @PostConstruct
    public void config() {
        settingsService.findAllDbConnectionSettings().forEach(settings -> {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(formJdbcUrl(settings));
            config.setUsername(settings.getUsername());
            config.setPassword(settings.getPassword());
            try {
                configs.put(settings.getId(), new HikariDataSource(config));
            } catch (Exception e) {
                logger.error("Initializaton of DB connetion with settings ID = [" + settings.getId() + "] failed. " + e.getMessage());
            }
        });
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
