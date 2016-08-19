package com.bjordan.template.highchart;

import com.yammer.metrics.core.HealthCheck;

public class AppHealthCheck extends HealthCheck {
  private final String template;

  public AppHealthCheck(String template) {
    super(template);
    this.template = template;
  }

  protected Result check() throws Exception {
    final String saying = String.format(template, "TEST");
    if (!saying.contains("TEST")) {
      return Result.unhealthy("template doesn't include a name");
    }
    return Result.healthy();
  }
}
