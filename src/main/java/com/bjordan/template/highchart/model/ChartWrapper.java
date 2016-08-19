package com.bjordan.template.highchart.model;

import com.google.common.collect.ImmutableList;

public class ChartWrapper {
  public final String chartName;
  public final ImmutableList<ChartData> data;

  public ChartWrapper(String chartName, ImmutableList<ChartData> data) {
    this.chartName = chartName;
    this.data = data;
  }
}
