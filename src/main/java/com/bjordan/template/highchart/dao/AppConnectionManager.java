package com.bjordan.template.highchart.dao;

import com.bjordan.template.highchart.config.DbConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class AppConnectionManager {
  private static final Logger log = LogManager.getLogger(AppConnectionManager.class);
  private final HikariDataSource dataSource;

  public AppConnectionManager(DbConfiguration configuration) {
    HikariConfig hikariConfig = new HikariConfig();

    hikariConfig.setDataSourceClassName(configuration.driver);
    hikariConfig.addDataSourceProperty("serverName", configuration.hostname);
    hikariConfig.addDataSourceProperty("port", configuration.port);
    hikariConfig.addDataSourceProperty("databaseName", configuration.dbName);
    hikariConfig.addDataSourceProperty("user", configuration.user);
    hikariConfig.addDataSourceProperty("password", configuration.password);
    hikariConfig.addDataSourceProperty("password", configuration.password);
    hikariConfig.setMaximumPoolSize(Integer.valueOf(configuration.connectionPoolSize));

    HikariDataSource dataSource = new HikariDataSource(hikariConfig);
    dataSource.setConnectionTimeout(Integer.valueOf(configuration.connectionTimeout));
    this.dataSource = dataSource;
  }

  public Connection getConnection() throws SQLException {
    return this.dataSource.getConnection();
  }
}
