package com.events.scheduler.util;

import com.events.scheduler.util.DateUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilityTest {
    @Test
    public void stringIsConvertedToDateProperlyWithRIghtFormat(){
        String dateString = "2021-12-17 06:00:00";
        Date date = DateUtility.StringToDate(dateString);
        Assert.assertEquals("Fri Dec 17 06:00:00 CST 2021", date.toString());
    }

    @Test
    public void stringIsConvertedToDateProperlyWithWrongFormat(){
        String dateString = "2021-12-17";
        Date date = DateUtility.StringToDate(dateString);
        Assert.assertNull(date);
    }

    @Test
    public void testDurationBetweenDates() {
        String dateString1 = "2021-12-17 06:00:00";
        String dateString2 = "2021-12-17 17:00:00";
        long expectedDuration = 660L;
        long durationInMin = DateUtility.getDurationInMinutes(dateString1,dateString2);
        Assert.assertEquals(expectedDuration, durationInMin);
    }

    @Test
    public void testDurationBetweenInvalidDates() {
        String dateString1 = "2021-12-17";
        String dateString2 = "2021-12-17 17:00:00";
        long expectedDuration = -1L;
        long durationInMin = DateUtility.getDurationInMinutes(dateString1,dateString2);
        Assert.assertEquals(expectedDuration, durationInMin);
    }
}