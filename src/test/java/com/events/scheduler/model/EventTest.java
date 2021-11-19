package com.events.scheduler.model;

import com.events.scheduler.model.Event;
import com.events.scheduler.model.EventAvailability;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventTest {
    Event event;
    EventAvailability eventAvailability;
    @Before
    public void setup() {
        event = new Event(123, "TestEvent", "Test Description", 20, 500.0);
        eventAvailability = new EventAvailability(5555, "City Hall,TestCity 7021","2021-12-17 06:00:00","2021-12-17 17:00:00",0 );
    }

    @Test
    public void testGetValidAvailabilities() {
        List<EventAvailability> list = new ArrayList<>();
        list.add(eventAvailability);
        EventAvailability eventAvailability2 = new EventAvailability(5557, "City Hall,TestCity 7021","2021-11-15 06:00:00","2021-11-15 17:00:00",0 );
        list.add(eventAvailability2);

        List<EventAvailability> validAvailabilities = event.getValidAvailabilities(list);

        Assert.assertEquals(1, validAvailabilities.size());
        Assert.assertEquals(5555, validAvailabilities.get(0).getId());
    }

    @Test
    public void testGetSelectedAvailability() {
        List<EventAvailability> list = new ArrayList<>();
        list.add(eventAvailability);
        event.setEventAvailability(list);
        Assert.assertEquals(eventAvailability, event.getSelectedAvailability());
    }

    @Test
    public void testAddAvailability() {
        event.addAvailability(eventAvailability, eventAvailability.getLocation());
        Assert.assertNotNull(event.getEventAvailability());
        Assert.assertEquals(1, event.getEventAvailability().size());
        Assert.assertEquals(5555, event.getEventAvailability().get(0).getId());
    }

    @Test
    public void testFinalPriceWithNoAvailabilitySelected() {
        event.setEventAvailability(new ArrayList<>());
        Assert.assertEquals(500.0, event.getPrice(),0);
        Assert.assertEquals(0.0, event.getFinalPrice(),0);
    }

    @Test
    public void testFinalPriceWithNoAvailability() {
        Assert.assertEquals(500.0, event.getPrice(),0);
        Assert.assertEquals(0.0, event.getFinalPrice(),0);
    }

    @Test
    public void testFinalPriceWithOneAvailabilitySelected() {
        event.addAvailability(eventAvailability, eventAvailability.getLocation());
        Assert.assertEquals(500.0, event.getPrice(),0);
        Assert.assertEquals(500.0, event.getFinalPrice(),0);
    }

    @Test
    public void testEventIsInvalidWhenNoAvailability() {
        event.setEventAvailability(null);
        Assert.assertFalse(event.isValid());
    }

    @Test
    public void testEventIsInvalidWhenInvalidId() {
        event.setId(0);
        event.addAvailability(eventAvailability, eventAvailability.getLocation());
        Assert.assertFalse(event.isValid());
    }

    @Test
    public void testEventIsInvalidWhenInvalidAvailabilityId() {
        EventAvailability availability = new EventAvailability(0, "City Hall,TestCity 7021","2021-12-17 06:00:00","2021-12-17 17:00:00",0 );
        event.addAvailability(availability, availability.getLocation());
        Assert.assertFalse(event.isValid());
    }

    @Test
    public void testEventIsValidWhenDataIsValid() {
        event.setEventAvailability(null);
        event.addAvailability(eventAvailability,eventAvailability.getLocation());
        Assert.assertTrue(event.isValid());
    }

}