package com.events.scheduler.db.events;

import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;

import java.util.List;
import java.util.Map;

public interface EventDataSource {
    List<Event> searchEvents(Organization organization, String keyword);
    List<Event> getAllEvents(Organization organization);
    Map<Integer,Event> getAllEventsAsMap(Organization organization);
    int createEvent(Organization organization, Event event);

    Event getEvent(Organization organization, Event event);
    Event getEventFromDetails(Organization organization, Event event);

    int updateEvent(Organization organization, Event event);
}
