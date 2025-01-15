package com.gdgoc.study_group.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

  @Bean
  public DataSource dataSource() {
    return ConnectorConnectionPoolFactory.createConnectionPool();
  }
}
