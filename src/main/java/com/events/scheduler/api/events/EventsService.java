package com.events.scheduler.api.events;

import com.events.scheduler.db.events.EventDataSource;
import com.events.scheduler.db.eventavailability.EventAvailabilityDataSource;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.EventAvailability;
import com.events.scheduler.model.Organization;

import java.util.ArrayList;
import java.util.List;

public final class EventsService {

    EventDataSource eventDataSource;
    EventAvailabilityDataSource eventAvailabilityDataSource;

    private static EventsService INSTANCE = null;
    private EventsService(){}
    private EventsService(EventDataSource source, EventAvailabilityDataSource availabilityDataSource) {
        eventDataSource = source;
        eventAvailabilityDataSource = availabilityDataSource;
    }

    public static EventsService getInstance(EventDataSource source, EventAvailabilityDataSource availabilityDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EventsService(source,availabilityDataSource);
        }
        return INSTANCE;
    }

    List<Event> searchEvent(Organization org, String keyword) {
        return eventDataSource.searchEvents(org, keyword);
    }

    int createEvent(Organization organization, Event event) {
        return eventDataSource.createEvent(organization, event);
    }

    int updateEvent(Organization organization, Event event) {
        return eventDataSource.updateEvent(organization, event);
    }

    public Event getEvent(Organization organization, Event event) {
        return eventDataSource.getEvent(organization, event);
    }

    public Event getEventFromDetails(Organization organization, Event event) {
        return eventDataSource.getEventFromDetails(organization, event);
    }

    public Event getEventWithAvailabilities(Organization organization, Event event) {
        Event reqEvent = getEvent(organization,event);
        List<EventAvailability> availabilities = eventAvailabilityDataSource.getEventAvailabilityForEvent(organization,event);
        reqEvent.setEventAvailability(reqEvent.getValidAvailabilities(availabilities));
        return reqEvent;
    }

    public Event getEventWithAvailability(Organization organization, int eventId, int availabilityId, String location) {
        Event reqEvent = getEvent(organization,new Event(eventId));
        EventAvailability availability = eventAvailabilityDataSource.getEventAvailability(organization, new EventAvailability(availabilityId));
        reqEvent.addAvailability(availability,location);
        return reqEvent;
    }

    public List<Event> getAllEventsWithAvailability(Organization organization) {
        List<Event> eventsList = searchEvent(organization, "");
        List<Event> finalList = new ArrayList<>();
        for(Event e : eventsList) {
            Event eventWithAvailability = getEventWithAvailabilities(organization,e);
            if (eventWithAvailability.getEventAvailability().size() > 0) {
                finalList.add(eventWithAvailability);
            }
        }
        return finalList;
    }

}
