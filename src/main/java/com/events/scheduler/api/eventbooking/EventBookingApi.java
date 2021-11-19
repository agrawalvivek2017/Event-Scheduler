package com.events.scheduler.api.eventbooking;

import com.events.scheduler.model.EventBooking;
import com.events.scheduler.model.PaymentDetails;
import com.events.scheduler.model.Response;

import java.util.Map;

public interface EventBookingApi {
    Response createBooking(String orgName,int customerId,int eventId, int availabilityId, Map<String,String> data);
    Response getBooking(String orgName,int customerId,int bookingId);
    Response makePayment(String orgName, int customerId, int bookingId, PaymentDetails details);
    Response updateBooking(String orgName,int customerId,int bookingId, int availabilityId, Map<String,String> data);
    Response cancelBooking(String orgName,int customerId,int bookingId);


}
