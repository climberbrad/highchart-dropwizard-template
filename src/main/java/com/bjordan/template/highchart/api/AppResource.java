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
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AppResource {
  private final String LINE_CHART_PATH= "line-chart";
  private final LineChartDao lineChartDao;
  private static final SimpleDateFormat DAY_MONTH_YEAR_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
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
  @Path(LINE_CHART_PATH)
  public Response postLineChart(String data) {
    String keyVals = URLDecoder.decode(data.replace("term=", ""));
    Map<String, String> keyMap = Splitter.on("&").withKeyValueSeparator("=").split(keyVals);


    String graphName = keyMap.get("graphName");
    long timestamp = parseDate(URLDecoder.decode(keyMap.get("timestamp")), DAY_MONTH_YEAR_FORMAT).getEpochSecond();
    double dataPoint = Double.valueOf(keyMap.get("dataPoint"));

    lineChartDao.insertLineGraphDataPoint(graphName,timestamp, dataPoint);
    return Response.ok().entity("inserted!").build();
  }



  private Instant parseDate(String date, DateFormat format) {
    try {
      return format.parse(date).toInstant();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
