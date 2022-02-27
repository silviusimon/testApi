package com.testapi.testapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@TestConfiguration
@TestPropertySource("classpath:application-test.properties")
@Profile({ "test" })
public class AccountTestConfiguration {

    @Value("${com.testapi.mysqldocker.image}")
    private String mysqlDockerImageName;
    @Value("${com.testapi.mysql.testdb.name}")
    private String dbName;
    @Value("${test.spring.datasource.username}")
    private String username;
    @Value("${test.spring.datasource.password}")
    private String password;

    @Bean
    public MySQLContainer getContainer(){
        MySQLContainer  mySQLContainer  = new MySQLContainer(mysqlDockerImageName)
                .withDatabaseName(dbName)
                .withUsername(username)
                .withPassword(password);
        mySQLContainer.start();
        return mySQLContainer;
    }

    @Bean
    public DataSource dataSource(MySQLContainer mySQLContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mySQLContainer.getDriverClassName());
        dataSource.setUrl(mySQLContainer.getJdbcUrl());
        dataSource.setUsername(mySQLContainer.getUsername());
        dataSource.setPassword(mySQLContainer.getPassword());
        return dataSource;
    }
}
