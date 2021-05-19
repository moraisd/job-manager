package com.payoneer.jobmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

  @Bean
  @Profile("test")
  public DataSource dataSource(
      @Value("${jdbc.url}") final String url,
      @Value("${jdbc.driverClassName}") final String driverClassName,
      @Value("${jdbc.username}") final String username,
      @Value("${jdbc.password}") final String password) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return dataSource;
  }
}
