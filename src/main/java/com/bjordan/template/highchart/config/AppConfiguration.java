package com.bjordan.template.highchart.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class AppConfiguration extends Configuration {
  @Valid
  private DbConfiguration database;

  @NotEmpty
  @JsonProperty
  private String template;

  @NotEmpty
  @JsonProperty
  private String defaultName = "Stranger";

  public String getTemplate() {
    return template;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public DbConfiguration getDatabase() {
    return database;
  }

  public void setDatabase(DbConfiguration database) {
    this.database = database;
  }
}
