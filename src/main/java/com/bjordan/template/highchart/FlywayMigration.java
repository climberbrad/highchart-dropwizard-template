package com.bjordan.template.highchart;

import com.bjordan.template.highchart.config.DbConfiguration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;

public class FlywayMigration {

  private FlywayMigration() {
  }

  public static Flyway getFlyway(DbConfiguration configuration) {
    String jdbcUrl = "jdbc:mysql://" + configuration.hostname + "/" + configuration.dbName
        + "?autoReconnect=true"
        + "&useUnicode=true"
        + "&characterEncoding=utf8"
        + "&rewriteBatchedStatements=true"
        + "&createDatabaseIfNotExist=true";

    Flyway flyway = new Flyway();
    flyway.setDataSource(jdbcUrl, configuration.user, configuration.password);
    flyway.setBaselineVersion(MigrationVersion.fromVersion("1"));
    flyway.setIgnoreFutureMigrations(true);
    flyway.setBaselineOnMigrate(true);

    return flyway;
  }

  public static void validate(DbConfiguration databaseConfig) throws FlywayException {
    Flyway flyway = getFlyway(databaseConfig);
    flyway.validate();
  }

  public static void migrate(DbConfiguration databaseConfig) throws FlywayException {
    Flyway flyway = getFlyway(databaseConfig);
    flyway.migrate();
  }
}
