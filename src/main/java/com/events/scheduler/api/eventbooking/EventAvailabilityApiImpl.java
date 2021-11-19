//package com.events.scheduler.api.eventschedule;
//
//import com.events.scheduler.api.events.EventsService;
//import com.events.scheduler.model.Event;
//import com.events.scheduler.model.EventAvailability;
//import com.events.scheduler.model.EventScheduleResp;
//import com.events.scheduler.model.Response;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import static com.events.scheduler.util.DateUtility.StringToDate;
//
//// todo schedule event from id in path and exclude id from object
//@RestController
//public class EventAvailabilityApiImpl implements EventAvailabilityApi {
//    EventsScheduleService eventsScheduleApiService = EventsScheduleService.getInstance();
//    private EventsService eventsService = EventsService.getInstance();
//
//    @RequestMapping(value = "{orgName}/events/schedule/{scheduleId}", method = RequestMethod.GET)
//    @Override
//    public Response<EventScheduleResp> getSchedule(@PathVariable String orgName,@PathVariable int scheduleId) {
//        if (orgName == null) {
//            return new Response(302, "Invalid organization");
//        }
//        if(scheduleId == 0) {
//            return new Response(304, "Invalid scheduling");
//        }
//        EventAvailability es = eventsScheduleApiService.getSchedule(orgName, scheduleId);
//        Event e = eventsService.getEvent(orgName, es.getEventId());
//        EventScheduleResp eventScheduleResp = eventsScheduleApiService.makeScheduleResp(es,e);
//        if (eventScheduleResp != null && es != null && e != null) {
//            return new Response(eventScheduleResp, 200);
//        } else  {
//            return new Response(306, "Some error occurred");
//        }
//    }
//
//    @RequestMapping(value = "{orgName}/events/{eventId}/schedule", method = RequestMethod.GET)
//    @Override
//    public Response<List<EventScheduleResp>> getScheduleForEvent(@PathVariable String orgName, @PathVariable int eventId) {
//        if (orgName == null) {
//            return new Response(302, "Invalid organization");
//        }
//        if(eventId == 0) {
//            return new Response(304, "Invalid eventId");
//        }
//        Event e = eventsService.getEvent(orgName, eventId);
//        List<EventScheduleResp> eventScheduleResps = eventsScheduleApiService.getSchedulesForEvent(orgName, e);
//        if (eventScheduleResps != null) {
//            return new Response(eventScheduleResps, 200);
//        } else  {
//            return new Response(306, "Some error occurred");
//        }
//    }
//
//    @RequestMapping(value = "{orgName}/events/schedule", method = RequestMethod.POST)
//    @Override
//    public Response<EventScheduleResp> createSchedule(@PathVariable String orgName, @RequestBody EventAvailability schedule) {
//        if (schedule == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null || schedule.getLocation() == null) {
//            return new Response<EventScheduleResp>(301, "Invalid data provided");
//        }
//        if (StringToDate(schedule.getStartDateTime()) == null || StringToDate(schedule.getEndDateTime()) == null) {
//            return new Response<EventScheduleResp>(301, "Invalid dates provided");
//        }
//        int rsp = eventsScheduleApiService.createSchedule(orgName, schedule);
//        if (rsp == 200) {
//            Event e = eventsService.getEvent(orgName, schedule.getEventId());
//            EventScheduleResp esr = eventsScheduleApiService.getSchedule(orgName, schedule,e);
//            return new Response<EventScheduleResp>(esr, 200);
//        } else {
//            return new Response<EventScheduleResp>(rsp, "Unable to create an event schedule. Make sure time is not conflicting with any other schedule");
//        }
//    }
//
//    @RequestMapping(value = "{orgName}/events/schedule/{scheduleId}", method = RequestMethod.PUT)
//    @Override
//    public Response<EventScheduleResp> updateSchedule(@PathVariable String orgName,@PathVariable int scheduleId, @RequestBody EventAvailability schedule) {
//        if (schedule == null || schedule.getStartDateTime() == null || schedule.getEndDateTime() == null || schedule.getLocation() == null) {
//            return new Response<EventScheduleResp>(301, "Invalid data provided");
//        }
//        if (StringToDate(schedule.getStartDateTime()) == null || StringToDate(schedule.getEndDateTime()) == null) {
//            return new Response<EventScheduleResp>(301, "Invalid dates provided");
//        }
//        EventAvailability es= eventsScheduleApiService.getSchedule(orgName, scheduleId);
//        if (es.getEventId() != schedule.getEventId()) {
//            return new Response<EventScheduleResp>(302, "Cannot change event");
//        }
//        int rsp = eventsScheduleApiService.updateSchedule(orgName, schedule, scheduleId);
//        if (rsp == 200) {
//            Event e = eventsService.getEvent(orgName, schedule.getEventId());
//            EventScheduleResp esr = eventsScheduleApiService.getSchedule(orgName, scheduleId, e);
//            return new Response<EventScheduleResp>(esr, 200);
//        } else {
//            return new Response<EventScheduleResp>(rsp, "Unable to create an event schedule. Make sure time is not conflicting with any other schedule");
//        }
//    }
//}
