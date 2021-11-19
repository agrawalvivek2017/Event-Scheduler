package com.events.scheduler.db.events;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.events.scheduler.util.Constants.*;

public class EventsSQLDataSource extends MySqlDataSource implements EventDataSource {
    @Override
    public List<Event> searchEvents(Organization organization, String keyword) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENTS_TABLE + " where " + EVENTS_TABLE_COL_2 + " like \"%"+ keyword + "%\"" );
            List<Event> events = new ArrayList<>();
            while (set.next()) {
                Event e = new Event(set.getInt(EVENTS_TABLE_COL_1),set.getString(EVENTS_TABLE_COL_2), set.getString(EVENTS_TABLE_COL_3),set.getInt(EVENTS_TABLE_COL_4),set.getDouble(EVENTS_TABLE_COL_5));
                events.add(e);
            }
            return events;
        }catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List<Event> getAllEvents(Organization organization) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENTS_TABLE);
            List<Event> events = new ArrayList<>();
            while (set.next()) {
                Event e = new Event(set.getInt(EVENTS_TABLE_COL_1),set.getString(EVENTS_TABLE_COL_2), set.getString(EVENTS_TABLE_COL_3),set.getInt(EVENTS_TABLE_COL_4),set.getDouble(EVENTS_TABLE_COL_5));
                events.add(e);
            }
            return events;
        } catch (Exception ex) {
           System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
    }


    public Map<Integer,Event> getAllEventsAsMap(Organization organization) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENTS_TABLE);
            Map<Integer,Event> events = new HashMap<>();
            while (set.next()) {
                Event e = new Event(set.getInt(EVENTS_TABLE_COL_1),set.getString(EVENTS_TABLE_COL_2), set.getString(EVENTS_TABLE_COL_3),set.getInt(EVENTS_TABLE_COL_4),set.getDouble(EVENTS_TABLE_COL_5));
                events.put(e.getId(),e);
            }
            return events;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int createEvent(Organization organization, Event event) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        ResultSet rs = null;
        try {
            rs = performQueryWithResult("insert into " + EVENTS_TABLE + "(" + EVENTS_TABLE_COL_2 + "," + EVENTS_TABLE_COL_3 + "," + EVENTS_TABLE_COL_4 + "," + EVENTS_TABLE_COL_5 + ")" +
                    " values('" + event.getTitle() + "','" + event.getDescription() + "','" + event.getCapacity() + "','" + event.getPrice()+ "')");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
    }

    @Override
    public Event getEvent(Organization organization, Event event) {
        openConnection(SQL_USERNAME,SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENTS_TABLE + " where " + EVENTS_TABLE_COL_1 + "="+ event.getId());
            while (set.next()) {
                return new Event(set.getInt(EVENTS_TABLE_COL_1),set.getString(EVENTS_TABLE_COL_2), set.getString(EVENTS_TABLE_COL_3),set.getInt(EVENTS_TABLE_COL_4),set.getDouble(EVENTS_TABLE_COL_5));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Event getEventFromDetails(Organization organization, Event event) {
        openConnection(SQL_USERNAME,SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENTS_TABLE + " where " + EVENTS_TABLE_COL_2 + "="+ event.getTitle() + " AND " + EVENTS_TABLE_COL_4 + "=" + event.getCapacity() + " AND " + EVENTS_TABLE_COL_5 + "=" + event.getPrice());
            while (set.next()) {
                return new Event(set.getInt(EVENTS_TABLE_COL_1),set.getString(EVENTS_TABLE_COL_2), set.getString(EVENTS_TABLE_COL_3),set.getInt(EVENTS_TABLE_COL_4),set.getDouble(EVENTS_TABLE_COL_5));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int updateEvent(Organization organization, Event event) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            performQuery("update " + EVENTS_TABLE + " set " + EVENTS_TABLE_COL_2 + "=\"" + event.getTitle()+ "\"," + EVENTS_TABLE_COL_3 + "=\"" + event.getDescription()+ "\"," + EVENTS_TABLE_COL_4 + "=" + event.getCapacity()+ ","+ EVENTS_TABLE_COL_5 + "=" + event.getPrice() + " WHERE EventId="+ event.getId());
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
    }


}
