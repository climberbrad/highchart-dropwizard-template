package com.bjordan.template.highchart.config;

import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;

public class AppConfiguration extends Configuration {
  @Valid
  private DbConfiguration database;

  public DbConfiguration getDatabase() {
    return database;
  }

}
