package com.bjordan.template.highchart.api;

import com.bjordan.template.highchart.dao.LineChartDao;
import com.bjordan.template.highchart.model.ChartWrapper;
import com.yammer.metrics.annotation.Timed;

import java.time.Instant;
import java.time.Period;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/graph")
@Produces(MediaType.APPLICATION_JSON)
public class AppResource {
  private final String template;
  private final String defaultName;
  private final AtomicLong counter;
  private final LineChartDao lineChartDao;

  public AppResource(String template, String defaultName, LineChartDao lineChartDao) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();

    this.lineChartDao = lineChartDao;
  }

  @GET
  public Response hi() {
    return Response.ok().entity("{\"timestamp\": 1471154400,\"dataPoint\": 185.9}").build();
  }

  @GET
  @Path("/line-chart")
  @Timed
  public Response lineChartData(
      @QueryParam("name") String name,
      @QueryParam("after") long afterInclusive,
      @QueryParam("before") long beforeInclusive) {
    long weekAgo = Instant.now().minus(Period.ofDays(45)).getEpochSecond();
    long today = Instant.now().getEpochSecond();

    ChartWrapper wrapper = lineChartDao.getGraphData(name, weekAgo, today);
    return Response.ok().entity(wrapper).build();
  }
}
