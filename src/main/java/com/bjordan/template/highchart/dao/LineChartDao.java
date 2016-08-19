package com.bjordan.template.highchart.dao;

import com.google.common.collect.ImmutableList;

import com.bjordan.template.highchart.model.ChartData;
import com.bjordan.template.highchart.model.ChartWrapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LineChartDao {
  private static final Logger log = LogManager.getLogger(LineChartDao.class);
  private final AppConnectionManager connectionManager;

  private static final String SELECT_LINE_GRAPH = "SELECT data_timestamp, data_value"
      + " FROM line_chart"
      + " WHERE graph_name = ? and data_timestamp >= ? and data_timestamp <= ?";

  private static final String INSERT_LINE_GRAPH = "INSERT into line_chart"
      + " values(?,?,?)";

  public LineChartDao(AppConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  public void insertLineGraphDataPoint(String graphName, long timestamp, double dataPoint) {
    try (Connection conn = connectionManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(INSERT_LINE_GRAPH)) {
      stmt.setString(1, graphName);
      stmt.setLong(2, timestamp);
      stmt.setDouble(3, dataPoint);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ChartWrapper getGraphData(String graphName, long afterInclusive, long beforeInclusive) {
    ImmutableList.Builder<ChartData> dataBuilder = ImmutableList.builder();

    try (Connection conn = connectionManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(SELECT_LINE_GRAPH)) {
      stmt.setString(1, graphName);
      stmt.setLong(2, afterInclusive);
      stmt.setLong(3, beforeInclusive);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          dataBuilder.add(new ChartData(rs.getLong(1), rs.getDouble(2)));
        }
      }
    } catch (SQLException ex) {
      log.error("Unable to execute query for line graph data.", ex);
    }
    return new ChartWrapper(graphName, dataBuilder.build());
  }
}
