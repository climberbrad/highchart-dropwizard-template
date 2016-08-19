package com.bjordan.template.highchart;

import com.bjordan.template.highchart.api.AppResource;
import com.bjordan.template.highchart.config.AppConfiguration;
import com.bjordan.template.highchart.dao.AppConnectionManager;
import com.bjordan.template.highchart.dao.FlywayMigration;
import com.bjordan.template.highchart.dao.LineChartDao;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class AppService extends Service<AppConfiguration> {
  public static void main(String[] args) throws Exception {
    new AppService().run(args);
  }

  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    bootstrap.setName("app");
    bootstrap.addBundle(new AssetsBundle("/webroot", "/charts", "index.html"));
  }

  @Override
  public void run(AppConfiguration configuration, Environment environment) {
    FlywayMigration.migrate(configuration.getDatabase());

    LineChartDao lineChartDao = new LineChartDao(
        new AppConnectionManager(configuration.getDatabase())
    );

    environment.addResource(new AppResource(lineChartDao));
    environment.addHealthCheck(new AppHealthCheck("app"));
  }
}
