package com.bjordan.template.highchart.api;

import com.google.common.base.Splitter;

import com.bjordan.template.highchart.dao.LineChartDao;
import com.bjordan.template.highchart.model.ChartWrapper;
import com.yammer.metrics.annotation.Timed;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AppResource {
  private final String LINE_CHART_PATH= "line-chart";
  private final LineChartDao lineChartDao;
  public static final SimpleDateFormat DAY_MONTH_YEAR_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
  private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public AppResource(LineChartDao lineChartDao) {
    this.lineChartDao = lineChartDao;
  }

  @GET
  public Response hi() {
    return Response.ok().entity("hi").build();
  }

  @GET
  @Path(LINE_CHART_PATH)
  @Timed
  public Response lineChartData(
      @QueryParam("name") String name,
      @QueryParam("after") String afterInclusive,
      @QueryParam("before") String beforeInclusive) {

    ChartWrapper wrapper = lineChartDao.getGraphData(
        name,
        parseDate(afterInclusive, YEAR_MONTH_DAY_FORMAT).getEpochSecond(),
        parseDate(beforeInclusive, YEAR_MONTH_DAY_FORMAT).getEpochSecond()
    );

    return Response.ok().entity(wrapper).build();
  }

  @POST
  @Path(LINE_CHART_PATH + "/{graphName}")
  public Response postLineChart(@PathParam("graphName") String graphName, String data) {
    String keyVals = URLDecoder.decode(data.replace("term=", ""));
    Map<String, String> keyMap = Splitter.on("&").withKeyValueSeparator("=").split(keyVals);

    String dateString = URLDecoder.decode(keyMap.get("timestamp"));
    long timestamp = parseDate(dateString, DAY_MONTH_YEAR_FORMAT).toEpochMilli()/1000;
    double dataPoint = Double.valueOf(keyMap.get("dataPoint"));

    lineChartDao.insertLineGraphDataPoint(graphName,timestamp, dataPoint);
    return Response.ok().entity("inserted!").build();
  }



  private Instant parseDate(String date, DateFormat format) {
    try {
      Date d = format.parse(date);
      return d.toInstant();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
