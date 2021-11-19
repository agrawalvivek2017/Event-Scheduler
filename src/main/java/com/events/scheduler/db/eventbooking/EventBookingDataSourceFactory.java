package com.events.scheduler.db.eventbooking;

import com.events.scheduler.db.eventavailability.EventAvailabilityDataSource;
import com.events.scheduler.db.events.EventDataSource;
import com.events.scheduler.db.payment.PaymentDataSource;

import static com.events.scheduler.util.Constants.SQL;

public class EventBookingDataSourceFactory {
    private EventBookingDataSourceFactory() {
    }

    private static EventBookingDataSourceFactory INSTANCE = null;

    public synchronized static EventBookingDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventBookingDataSourceFactory();
        }
        return INSTANCE;
    }

    public EventBookingDataSource getEventBookingDataSource(String type, EventDataSource eventDataSource,
                                                            EventAvailabilityDataSource availabilityDataSource, PaymentDataSource paymentDataSource) {
        if (type.equalsIgnoreCase(SQL)) {
            return new EventBookingSQLDataSource(eventDataSource, availabilityDataSource, paymentDataSource);
        }
        return null;
    }

}
