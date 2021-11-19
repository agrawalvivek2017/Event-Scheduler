package com.events.scheduler.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventBookingTest {
    private EventBooking eventBooking;
    private Event event = new Event(123, "TestEvent", "Test Description", 20, 500.0);
    private EventAvailability eventAvailability = new EventAvailability(5555, "City Hall,TestCity 7021","2021-12-17 06:00:00","2021-12-17 17:00:00",0 );
    private Customer c= new Customer(1000);
    @Before
    public void setup() {
        event.addAvailability(eventAvailability,eventAvailability.getLocation());
        eventBooking = new EventBooking(100,event, null, 50000, c,System.currentTimeMillis() , 0);
    }
    @Test
    public void testCreatePayment() {
        Payment.PaymentMode mode = Payment.PaymentMode.CREDIT_CARD;
        double amount = 500.0;
        Payment p = eventBooking.createPayment(mode, amount);
        Assert.assertNotNull(p);
        Assert.assertEquals(500.0,p.getAmount(),0);
    }

    @Test
    public void testCreatePaymentAfterAddingPayment() {
        Payment.PaymentMode mode = Payment.PaymentMode.CREDIT_CARD;
        double amount = 500.0;
        Payment p = eventBooking.createPayment(mode, amount);
        eventBooking.setStatus(2);
        Payment p2 = eventBooking.createPayment(mode, amount);
        Assert.assertNull(p2);
    }

    @Test
    public void testIsCancelled() {
        eventBooking.setStatus(1);
        Assert.assertTrue(eventBooking.isCancelled());
    }

    @Test
    public void testIsNotCancelled() {
        eventBooking.setStatus(0);
        Assert.assertFalse(eventBooking.isCancelled());
    }

    @Test
    public void testIsPast() {
        EventAvailability eventAvailability2 = new EventAvailability(5555, "City Hall,TestCity 7021","2021-11-17 06:00:00","2021-11-17 17:00:00",0 );
        List<EventAvailability> list =new ArrayList<>();
        list.add(eventAvailability2);
        event.setEventAvailability(list);
        eventBooking.setEvent(event);
        Assert.assertTrue(eventBooking.isPast());
    }

    @Test
    public void testIsNotPast() {
        event.setEventAvailability(null);
        event.addAvailability(eventAvailability,eventAvailability.getLocation());
        eventBooking.setEvent(event);
        Assert.assertFalse(eventBooking.isPast());
    }

    @Test
    public void testVerifyCustomerSuccess() {
        int customerId = 1000;
        Assert.assertTrue(eventBooking.verifyCustomer(customerId));
    }

    @Test
    public void testVerifyCustomerFail() {
        int customerId = 1000 + 1;
        Assert.assertFalse(eventBooking.verifyCustomer(customerId));
    }

}