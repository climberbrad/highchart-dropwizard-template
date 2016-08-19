package com.bjordan.template.highchart;

import com.bjordan.template.highchart.api.AppResource;
import com.bjordan.template.highchart.config.AppConfiguration;
import com.bjordan.template.highchart.dao.AppConnectionManager;
import com.bjordan.template.highchart.dao.LineChartDao;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Period;
import java.util.Random;

public class AppService extends Service<AppConfiguration> {
  public static void main(String[] args) throws Exception {
    new AppService().run(args);
  }

  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    bootstrap.setName("app");
    bootstrap.addBundle(new AssetsBundle("/webroot", "/html", "index.html"));
  }

  @Override
  public void run(AppConfiguration configuration, Environment environment) {
    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();

    // create the DB connection and migrate the schema
    AppConnectionManager connectionManager = new AppConnectionManager(configuration.getDatabase());
    FlywayMigration.migrate(configuration.getDatabase());


    LineChartDao lineChartDao = new LineChartDao(connectionManager);
    insertDummyData(lineChartDao);
    environment.addResource(new AppResource(template, defaultName, lineChartDao));
    environment.addHealthCheck(new AppHealthCheck(template));
  }

  public void insertDummyData(LineChartDao dao) {
    Instant start = Instant.now().minus(Period.ofDays(25));

    String graphName = "test-chart-1";
    for (int i = 0; i < 25; i++) {
      dao.insertLineGraphDataPoint(graphName, start.plusSeconds((60 * 60 * 24) * i).getEpochSecond(), randomDouble());
    }
  }

  private double randomDouble() {
    Random r = new Random();
    double rangeMin = 1.0;
    double rangeMax = 100.0;

    BigDecimal bigDecimal = new BigDecimal(rangeMin + (rangeMax - rangeMin) * r.nextDouble());
    bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);

    return bigDecimal.doubleValue();
  }
}
