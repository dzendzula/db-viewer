package org.dzendzula.restfilterindex.connector;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dzendzula.restfilterindex.domain.DbConnectionSettingsBo;
import org.dzendzula.restfilterindex.service.DbConnectionSettingsService;
import org.dzendzula.restfilterindex.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DbConnectionPool {

    private Logger logger = LoggerFactory.getLogger(DbConnectionPool.class);

    @Autowired
    private DbConnectionSettingsService settingsService;

    public Connection getConnection(Long settingsId) {
        if (settingsId == null || settingsId < 0L) {
            return null;
        }
        DbConnectionSettingsBo settings = settingsService.getDbConnectionSettings(settingsId);
        if (settings == null) {
            return null;
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(formJdbcUrl(settings));
        config.setUsername(settings.getUsername());
        config.setPassword(settings.getPassword());
        HikariDataSource ds = new HikariDataSource(config);
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("Connection cannot be established. " + e.getMessage());
        }
        return null;
    }

    private String formJdbcUrl(DbConnectionSettingsBo settings) {
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
