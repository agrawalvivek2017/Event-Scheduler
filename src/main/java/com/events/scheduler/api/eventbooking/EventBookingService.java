package com.events.scheduler.api.eventbooking;

import com.events.scheduler.db.eventavailability.EventAvailabilityDataSource;
import com.events.scheduler.db.eventbooking.EventBookingDataSource;
import com.events.scheduler.db.payment.PaymentDataSource;
import com.events.scheduler.model.*;

public final class EventBookingService {

    private static EventBookingService INSTANCE = null;

    private EventBookingService(){}

    EventBookingDataSource eventBookingDataSource;
    PaymentDataSource paymentDataSource;
    EventAvailabilityDataSource availabilityDataSource;

    private EventBookingService(EventAvailabilityDataSource availabilityDataSource,
                                EventBookingDataSource eventBookingDataSource,
                                PaymentDataSource paymentDataSource) {
        this.eventBookingDataSource = eventBookingDataSource;
        this.paymentDataSource = paymentDataSource;
        this.availabilityDataSource = availabilityDataSource;
    }

    public static synchronized EventBookingService getInstance(EventAvailabilityDataSource availabilityDataSource, EventBookingDataSource eventBookingDataSource, PaymentDataSource paymentDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EventBookingService(availabilityDataSource, eventBookingDataSource,paymentDataSource);
        }
        return INSTANCE;
    }

    EventBooking createBooking(Organization org, Event e, Customer c) {
        e.getSelectedAvailability().setStatus(1);
        availabilityDataSource.updateEventAvailability(org, e.getSelectedAvailability());
        return eventBookingDataSource.createBooking(org, e, c);
    }

    EventBooking modifyBooking(Organization organization, EventBooking booking, Payment.PaymentMode type, double amount) {
        Payment payment = booking.createPayment(type, amount);
        if (payment != null) {
            booking.setStatus(2);
            return eventBookingDataSource.updateBooking(organization, booking, payment);
        }
        return null;
    }

    EventBooking getBooking(Organization org, int bookingId) {
        return eventBookingDataSource.getBooking(org, bookingId);
    }

    EventBooking updateBooking(Organization organization, EventBooking eventBooking, Event event, Customer c) {
        EventAvailability oldAvail = eventBooking.getEvent().getSelectedAvailability();
        eventBooking.setEvent(event);
        EventBooking updateBooking = eventBookingDataSource.updateBooking(organization, eventBooking, c);
        if (updateBooking != null) {
            oldAvail.reset();
            int resp = availabilityDataSource.updateEventAvailability(organization,oldAvail);
            if (resp == 200) {
                return updateBooking;
            }
        }
        return null;
    }

    int cancelBooking(Organization organization, EventBooking booking) {
        booking.getEvent().getSelectedAvailability().reset();
       return eventBookingDataSource.cancelBooking(organization,booking);
    }
}
