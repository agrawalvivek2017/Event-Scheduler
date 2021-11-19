package com.events.scheduler.db.eventavailability;

import com.events.scheduler.model.Event;
import com.events.scheduler.model.EventAvailability;
import com.events.scheduler.model.Organization;

import java.util.List;

public interface EventAvailabilityDataSource {
    List<EventAvailability> getEventAvailabilityForEvent(Organization organization, Event event);
    EventAvailability getEventAvailability(Organization organization, EventAvailability eventAvailability);
    int updateEventAvailability(Organization org,EventAvailability availability);
}
