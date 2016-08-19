package com.bjordan.template.highchart.model;

public class ChartData {
  public final long timestamp;
  public final double dataPoint;

  public ChartData(long timestamp, double dataPoint) {
    this.timestamp = timestamp;
    this.dataPoint = dataPoint;
  }
}
