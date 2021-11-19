package com.events.scheduler.db.eventavailability;

import static com.events.scheduler.util.Constants.SQL;

public class EventAvailabilityDataSourceFactory {
    private EventAvailabilityDataSourceFactory() {}

    private static  EventAvailabilityDataSourceFactory INSTANCE = null;

    public synchronized static EventAvailabilityDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventAvailabilityDataSourceFactory();
        }
        return INSTANCE;
    }

    public EventAvailabilityDataSource getEventAvailabilityDataSource(String type) {
        if(type.equalsIgnoreCase(SQL)) {
            return new EventAvailabilitySQLDataSource();
        }
        return null;
    }
}
