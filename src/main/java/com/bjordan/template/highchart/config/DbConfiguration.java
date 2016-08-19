package com.bjordan.template.highchart.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

public class DbConfiguration extends Configuration {
  @NotEmpty
  @JsonProperty
  public String hostname;

  @NotEmpty
  @JsonProperty
  public String port;

  @NotEmpty
  @JsonProperty
  public String dbName;

  @NotEmpty
  @JsonProperty
  public String connectionTimeout;

  @NotEmpty
  @JsonProperty
  public String connectionPoolSize;

  @NotEmpty
  @JsonProperty
  public String driver;

  @NotEmpty
  @JsonProperty
  public String user;

  @JsonProperty
  public String password = "";

}
