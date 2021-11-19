package com.events.scheduler.db.eventavailability;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.EventAvailability;
import com.events.scheduler.model.Organization;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.events.scheduler.util.Constants.*;

public class EventAvailabilitySQLDataSource extends MySqlDataSource implements EventAvailabilityDataSource {
    @Override
    public List<EventAvailability> getEventAvailabilityForEvent(Organization organization, Event event) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        List<EventAvailability> eventSchedules = new ArrayList<>();
        try {
            ResultSet rs = requestData("select * from " + EVENT_SCHEDULE_TABLE + " where " + EVENT_SCHEDULE_TABLE_COL_2+"="+ event.getId() + " AND " + EVENT_SCHEDULE_TABLE_COL_6 + "=0");
            while (rs.next()) {
                EventAvailability schedule = new EventAvailability(rs.getInt(EVENT_SCHEDULE_TABLE_COL_1), rs.getString(EVENT_SCHEDULE_TABLE_COL_3), rs.getString(EVENT_SCHEDULE_TABLE_COL_4), rs.getString(EVENT_SCHEDULE_TABLE_COL_5), rs.getInt(EVENT_SCHEDULE_TABLE_COL_6));
                eventSchedules.add(schedule);
            }
            return eventSchedules;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public EventAvailability getEventAvailability(Organization organization, EventAvailability availability) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet rs = requestData("select * from " + EVENT_SCHEDULE_TABLE + " where " + EVENT_SCHEDULE_TABLE_COL_1+"="+ availability.getId());
            if (rs.next()) {
                EventAvailability schedule = new EventAvailability(rs.getInt(EVENT_SCHEDULE_TABLE_COL_1), rs.getString(EVENT_SCHEDULE_TABLE_COL_3), rs.getString(EVENT_SCHEDULE_TABLE_COL_4), rs.getString(EVENT_SCHEDULE_TABLE_COL_5), rs.getInt(EVENT_SCHEDULE_TABLE_COL_6));
                return schedule;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int updateEventAvailability(Organization org, EventAvailability availability) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            performQuery("update " + EVENT_SCHEDULE_TABLE + " set " + EVENT_SCHEDULE_TABLE_COL_3 + "=\"" + availability.getLocation()+ "\"," + EVENT_SCHEDULE_TABLE_COL_6 + "=" + availability.getStatus()  + " WHERE EventScheduleId="+ availability.getId());
            return 200;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return 400;
    }

}
