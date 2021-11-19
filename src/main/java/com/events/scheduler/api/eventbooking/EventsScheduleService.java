//package com.events.scheduler.api.eventschedule;
//
//import com.events.scheduler.db.eventschedule.EventAvailabilityDataSource;
//import com.events.scheduler.db.eventschedule.EventAvailabilitySQLDataSource;
//import com.events.scheduler.model.Event;
//import com.events.scheduler.model.EventAvailability;
//import com.events.scheduler.model.EventScheduleResp;
//import com.events.scheduler.model.Organization;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.events.scheduler.util.DateUtility.StringToDate;
//import static com.events.scheduler.util.DateUtility.getDurationInMinutes;
//
//public class EventsScheduleService {
//
//    private EventAvailabilityDataSource dataSource;
//    private static EventsScheduleService INSTANCE;
//
//    private EventsScheduleService() {
//        dataSource = new EventAvailabilitySQLDataSource();
//    }
//
//    public static EventsScheduleService getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new EventsScheduleService();
//        }
//        return INSTANCE;
//    }
//
//    List<EventScheduleResp> getSchedulesForEvent(Organization organization, Event e) {
//        List<EventAvailability> list = dataSource.getEventSchedulesForEvent(organization, e.getId());
//        List<EventScheduleResp> esr = new ArrayList<>();
//        for(EventAvailability schedule : list) {
//            esr.add(new EventScheduleResp(schedule.getId(),e,schedule.getLocation(),getDurationInMinutes(schedule.getStartDateTime(),schedule.getEndDateTime()),StringToDate(schedule.getStartDateTime()).getTime()));
//        }
//        return esr;
//    }
//
//    EventAvailability getSchedule(String orgName, int scheduleId) {
//        return dataSource.getEventSchedule(orgName, scheduleId);
//    }
//
//    EventScheduleResp makeScheduleResp(EventAvailability schedule, Event e) {
//        return new EventScheduleResp(schedule.getId(),e,schedule.getLocation(),getDurationInMinutes(schedule.getStartDateTime(),schedule.getEndDateTime()),StringToDate(schedule.getStartDateTime()).getTime());
//    }
//
//    EventScheduleResp getSchedule(String orgName, int scheduleId, Event e) {
//        EventAvailability schedule = dataSource.getEventSchedule(orgName, scheduleId);
//        return new EventScheduleResp(schedule.getId(),e,schedule.getLocation(),getDurationInMinutes(schedule.getStartDateTime(),schedule.getEndDateTime()),StringToDate(schedule.getStartDateTime()).getTime());
//    }
//
//    EventScheduleResp getSchedule(String orgName, EventAvailability eventSchedule, Event e) {
//        EventAvailability schedule = dataSource.getEventSchedule(orgName, eventSchedule.getEventId(), eventSchedule.getStartDateTime(),eventSchedule.getEndDateTime());
//        return new EventScheduleResp(schedule.getId(),e,schedule.getLocation(),getDurationInMinutes(schedule.getStartDateTime(),schedule.getEndDateTime()),StringToDate(schedule.getStartDateTime()).getTime());
//    }
//
//
//    int createSchedule(String orgName, EventAvailability schedule) {
//        boolean isValidSchedule = true;
//        List<EventAvailability> list = dataSource.getEventSchedulesForEvent(orgName, schedule.getEventId());
//        long startTime = StringToDate(schedule.getStartDateTime()).getTime();
//        long endTime = StringToDate(schedule.getEndDateTime()).getTime();
//        for(EventAvailability eventAvailability : list) {
//            if (!eventAvailability.isValidSchedule(startTime,endTime)) {
//                isValidSchedule = false;
//                break;
//            }
//        }
//        if (isValidSchedule) {
//            return dataSource.createEventSchedule(orgName, schedule, schedule.getEventId());
//        } else  {
//            return 400;
//        }
//    }
//
//    int updateSchedule(String orgName, EventAvailability schedule, int scheduleId) {
//        boolean isValidSchedule = true;
//        List<EventAvailability> list = dataSource.getEventSchedulesForEvent(orgName, schedule.getEventId());
//        long startTime = StringToDate(schedule.getStartDateTime()).getTime();
//        long endTime = StringToDate(schedule.getEndDateTime()).getTime();
//        for(EventAvailability eventAvailability : list) {
//            if (!eventAvailability.isValidSchedule(startTime,endTime) && eventAvailability.getId() != scheduleId) {
//                isValidSchedule = false;
//                break;
//            }
//        }
//        if (isValidSchedule) {
//            return dataSource.updateSchedule(orgName, schedule, scheduleId);
//        } else  {
//            return 400;
//        }
//    }
//
//
////    private boolean isValidSchedule(EventSchedule schedule, long startTime, long endTime) {
////        if(schedule == null) return false;
////        long eStartTime = StringToDate(schedule.getStartDateTime()).getTime();
////
////        long eEndTime = StringToDate(schedule.getEndDateTime()).getTime();
////        // we make sure the same event can be scheduled only after 4 hours after ending prev schedule hence add  14400000
////        // also start time should atleast be 24 hours ahead and duration should atleast be one hour
////        return (startTime > System.currentTimeMillis() + 86400000L ) && (endTime - startTime > 3600000L) &&(startTime + 14400000 < eStartTime || startTime > (eEndTime + 14400000)) &&
////                (endTime + 14400000L < eStartTime || endTime > eEndTime + 14400000L);
////    }
//
//}
