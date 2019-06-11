package org.dzendzula.dbviewer.testconfig;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
public class TestDbConfiguration {

    @Value("${test.datasource.url}")
    private String url;
    @Value("${test.datasource.driver-class-name}")
    private String driver;
    @Value("${test.datasource.username}")
    private String user;
    @Value("${test.datasource.password}")
    private String password;

    @Bean(name = "testDataSource")
    @ConfigurationProperties(prefix = "test.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "testEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean testEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("testDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).build();
    }

    @Bean(name = "testTransactionManager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("testEntityManagerFactory") EntityManagerFactory productEntityManagerFactory
    ) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }
}