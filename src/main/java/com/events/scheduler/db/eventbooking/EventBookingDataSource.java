package com.events.scheduler.db.eventbooking;

import com.events.scheduler.model.*;

import java.util.List;

public interface EventBookingDataSource {
    EventBooking getBooking(Organization organization,int bookingId);
    EventBooking updateBooking(Organization organization, EventBooking booking, Customer c);
    EventBooking updateBooking(Organization organization, EventBooking booking, Payment p);
    EventBooking createBooking(Organization organization,Event event, Customer c);
    List<EventBooking> getAllBookingsForCustomer(Organization organization, Customer c);
    int cancelBooking(Organization organization,EventBooking booking);
}
