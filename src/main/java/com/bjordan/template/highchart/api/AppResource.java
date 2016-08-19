package com.bjordan.template.highchart.api;

import com.bjordan.template.highchart.dao.LineChartDao;
import com.bjordan.template.highchart.model.ChartWrapper;
import com.yammer.metrics.annotation.Timed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AppResource {
  private final LineChartDao lineChartDao;

  public AppResource(LineChartDao lineChartDao) {
    this.lineChartDao = lineChartDao;
  }

  @GET
  public Response hi() {
    return Response.ok().entity("hi").build();
  }

  @GET
  @Path("line-chart")
  @Timed
  public Response lineChartData(
      @QueryParam("name") String name,
      @QueryParam("after") String afterInclusive,
      @QueryParam("before") String beforeInclusive) {

    ChartWrapper wrapper = lineChartDao.getGraphData(
        name,
        parseDate(afterInclusive).getEpochSecond(),
        parseDate(beforeInclusive).getEpochSecond()
    );

    return Response.ok().entity(wrapper).build();
  }

  private Instant parseDate(String date) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return format.parse(date).toInstant();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
