package com.bjordan.template.highchart.model;

public class ChartData {
  public final long timestamp;
  public final double dataPoint;

  private ChartData(Builder builder) {
    timestamp = builder.timestamp;
    dataPoint = builder.dataPoint;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private long timestamp;
    private double dataPoint;

    private Builder() {
    }

    public Builder withTimestamp(long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder withDataPoint(double dataPoint) {
      this.dataPoint = dataPoint;
      return this;
    }

    public ChartData build() {
      return new ChartData(this);
    }
  }
}
