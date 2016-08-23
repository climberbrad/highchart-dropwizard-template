package com.bjordan.template.highchart;

import static com.bjordan.template.highchart.api.AppResource.DAY_MONTH_YEAR_FORMAT;

import org.junit.Test;

import java.text.ParseException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void parseDate() throws ParseException {
        String dateString = "08/22/2016";

        long timestamp = DAY_MONTH_YEAR_FORMAT.parse(dateString).toInstant().getEpochSecond();
        System.out.println("here");
    }
}
