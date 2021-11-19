package com.events.scheduler.api.events;

import com.events.scheduler.model.Event;
import com.events.scheduler.model.Response;

import java.util.List;

public interface EventsApi {
    Response searchEvents(String orgName, String keyword);
    Response getAllEvents(String orgName);
    Response createEvent(String orgName, Event event);
    Response getEvent(String orgName, int eventId);
    Response updateEvent(String orgName, int eventId, Event event);
    Response getAvailabilityForEvent(String orgName,int eventId);
    Response getAllEventsWithAvailability(String orgName);
}
