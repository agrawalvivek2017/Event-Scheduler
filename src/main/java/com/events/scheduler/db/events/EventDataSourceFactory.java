package com.events.scheduler.db.events;

import static com.events.scheduler.util.Constants.SQL;

public final class EventDataSourceFactory {
    private EventDataSourceFactory() {}

    private static  EventDataSourceFactory INSTANCE = null;

    public synchronized static EventDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventDataSourceFactory();
        }
        return INSTANCE;
    }

    public EventDataSource getEventDataSource(String type) {
        if(type.equalsIgnoreCase(SQL)) {
            return new EventsSQLDataSource();
        }
        return null;
    }

}
