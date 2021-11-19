package com.events.scheduler.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventAvailabilityTest {


        EventAvailability eventAvailability;
        @Before
        public void setup() {
            eventAvailability = new EventAvailability(5555, "City Hall,TestCity 7021","2021-11-17 06:00:00","2021-12-17 17:00:00",0 );
        }

        @Test
        public void testisAvailable() {
            Assert.assertTrue(eventAvailability.isPastAvailability(1637359133000L));
        }

        @Test
        public void testisNotAvailable() {
            eventAvailability.setStartDateTime("2021-12-17 06:00:00");
            eventAvailability.setEndDateTime("2021-11-17 15:00:00");
            Assert.assertFalse(eventAvailability.isPastAvailability(1637359133000L));
        }

        @Test
        public void testreset() {
            eventAvailability.setStatus(1);
            eventAvailability.reset();
            Assert.assertEquals(0,eventAvailability.getStatus());
            Assert.assertNull(eventAvailability.getLocation());
        }

}