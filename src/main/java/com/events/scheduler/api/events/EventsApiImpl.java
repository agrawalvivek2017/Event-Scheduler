package com.events.scheduler.api.events;

import com.events.scheduler.api.organization.OrganizationService;
import com.events.scheduler.db.organization.OrganizationServiceDataSourceFactory;
import com.events.scheduler.db.eventavailability.EventAvailabilityDataSourceFactory;
import com.events.scheduler.db.events.EventDataSourceFactory;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.events.scheduler.util.Constants.SQL;

@RestController
public class EventsApiImpl implements EventsApi {

    OrganizationServiceDataSourceFactory orgFactory = OrganizationServiceDataSourceFactory.getInstance();
    OrganizationService organizationService = OrganizationService.getInstance(orgFactory.getOrganizationServiceDataSource(SQL));
    EventDataSourceFactory eventDataSourceFactory = EventDataSourceFactory.getInstance();
    EventsService eventsService = EventsService.getInstance(eventDataSourceFactory.getEventDataSource(SQL), EventAvailabilityDataSourceFactory.getInstance().getEventAvailabilityDataSource(SQL));


    @RequestMapping(value = "{orgName}/events/search", method = RequestMethod.GET)
    @Override
    public Response<List<Event>> searchEvents(@PathVariable String orgName, @RequestParam(required = false) String keyword) {
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        if(keyword == null) {
            keyword = "";
        }
        List<Event> events =  eventsService.searchEvent(org,keyword);
        if(events == null) {
            return new Response(305, "Invalid request");
        }
        return new Response<List<Event>>(events,200);
    }


    @RequestMapping(value = "{orgName}/events", method = RequestMethod.GET)
    @Override
    public Response<List<Event>> getAllEvents(@PathVariable String orgName) {
       return searchEvents(orgName, null);
    }

    @RequestMapping(value = "{orgName}/events", method = RequestMethod.POST)
    @Override
    public Response<Event> createEvent(@PathVariable String orgName,@RequestBody Event event) {

        if (orgName.isEmpty() || event == null || event.getTitle() == null || event.getTitle().isEmpty() || event.getCapacity() == 0 || event.getPrice() == 0.0) {
            return new Response<Event>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        int resp = eventsService.createEvent(org,event);
        if (resp > 1000) {
            return getEvent(orgName,resp);
        } else if (resp == 200) {
            Event e = eventsService.getEvent(org, event);
            return new Response<Event>( e,200);
        }
        else {
            return new Response<>(resp, "Some error occurred");
        }
    }

    @RequestMapping(value = "{orgName}/events/{eventId}", method = RequestMethod.PUT)
    @Override
    public Response<Event> updateEvent(@PathVariable String orgName,@PathVariable int eventId,@RequestBody Event event) {
        if (orgName.isEmpty() || event == null || event.getTitle() == null || event.getTitle().isEmpty() || event.getCapacity() == 0 || event.getPrice() == 0.0) {
            return new Response<Event>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        event.setId(eventId);
        int resp = eventsService.updateEvent(org,event);
        if (resp == 200) {
            Event e= eventsService.getEvent(org,event);
            if (e == null) {
                return new Response<>(306, "Unable to find record");
            }
            return new Response<Event>( e,200);
        }
        else {
            return new Response<Event>(305, "Some error occurred");
        }
    }

    @RequestMapping(value = "{orgName}/events/{eventId}/availability", method = RequestMethod.GET)
    @Override
    public Response<Event> getAvailabilityForEvent(@PathVariable String orgName,@PathVariable int eventId) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(eventId == 0) {
            return new Response(304, "Invalid event");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Event eventToGet = new Event();
        eventToGet.setId(eventId);
        Event e = eventsService.getEventWithAvailabilities(org, eventToGet);
        if(e == null) {
            return new Response<>(306, " No such event");
        } else {
            return new Response<>(e, 200);
        }
    }

    @RequestMapping(value = "{orgName}/events/availability", method = RequestMethod.GET)
    @Override
    public Response<List<Event>> getAllEventsWithAvailability(@PathVariable String orgName) {

        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        List<Event> eventList = eventsService.getAllEventsWithAvailability(org);
        if(eventList == null) {
            return new Response<>(306, " No events available");
        } else {
            return new Response<>(eventList, 200);
        }
    }

    @RequestMapping(value = "{orgName}/events/{eventId}", method = RequestMethod.GET)
    @Override
    public Response<Event> getEvent(@PathVariable String orgName, @PathVariable int eventId) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(eventId == 0) {
            return new Response(304, "Invalid event");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Event eventToGet = new Event();
        eventToGet.setId(eventId);
        Event e = eventsService.getEvent(org,eventToGet);
        if(e == null) {
            return new Response<>(306, " No such event");
        } else {
            return new Response<>(e, 200);
        }
    }



}
