package com.events.scheduler.db.eventbooking;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.db.eventavailability.EventAvailabilityDataSource;
import com.events.scheduler.db.events.EventDataSource;
import com.events.scheduler.db.payment.PaymentDataSource;
import com.events.scheduler.model.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.events.scheduler.util.Constants.*;

public class EventBookingSQLDataSource extends MySqlDataSource implements EventBookingDataSource {

    private EventAvailabilityDataSource availabilityDataSource;
    private EventDataSource eventDataSource;
    private PaymentDataSource paymentDataSource;
    public EventBookingSQLDataSource(EventDataSource eventDataSource, EventAvailabilityDataSource availabilityDataSource, PaymentDataSource paymentDataSource) {
        this.eventDataSource = eventDataSource;
        this.availabilityDataSource = availabilityDataSource;
        this.paymentDataSource = paymentDataSource;
    }


    @Override
    public EventBooking getBooking(Organization organization, int bookingId){
        if (bookingId == 0) return null;
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + EVENT_BOOKING_TABLE + " where " + EVENT_BOOKING_TABLE_COL_1 + "="+ bookingId);
            if (set.next()) {
                int eventId = set.getInt(EVENT_BOOKING_TABLE_COL_2);
                int availabilityId = set.getInt(EVENT_BOOKING_TABLE_COL_3);
                Event event = eventDataSource.getEvent(organization, new Event(eventId));
                EventAvailability availability = availabilityDataSource.getEventAvailability(organization,new EventAvailability(availabilityId));
                event.addAvailability(availability, availability.getLocation());
                Payment payment = paymentDataSource.getPayment(organization, set.getInt(EVENT_BOOKING_TABLE_COL_4));
                return new EventBooking(set.getInt(EVENT_BOOKING_TABLE_COL_1),event,payment, set.getDouble(EVENT_BOOKING_TABLE_COL_5),new Customer(set.getInt(EVENT_BOOKING_TABLE_COL_6)),set.getLong(EVENT_BOOKING_TABLE_COL_7),set.getInt(EVENT_BOOKING_TABLE_COL_8));
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
    public EventBooking updateBooking(Organization organization, EventBooking booking, Customer c) {
        try {
            EventAvailability availability = booking.getEvent().getSelectedAvailability();
            availability.setStatus(1);
            availabilityDataSource.updateEventAvailability(organization,availability);
            openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
            performQuery("update " + EVENT_BOOKING_TABLE + " set " + EVENT_BOOKING_TABLE_COL_3 + "=" + availability.getId() + ", " +EVENT_BOOKING_TABLE_COL_7 + "=" + System.currentTimeMillis() +  " WHERE BookingId="+ booking.getBookingId());
            return getBooking(organization,booking.getBookingId());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }


    @Override
    public EventBooking updateBooking(Organization organization, EventBooking booking, Payment p) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            if (p.getBooking() == null || p.getBooking().getBookingId() != booking.getBookingId())
                p.setBooking(booking);
            Payment payment = paymentDataSource.addPayment(organization, p);
            if (payment == null || payment.getPaymentId() == 0) return null;
            performQuery("update " + EVENT_BOOKING_TABLE + " set " + EVENT_BOOKING_TABLE_COL_4 + "=" + payment.getPaymentId() + " , " + EVENT_BOOKING_TABLE_COL_8 +"="+booking.getStatus() + " WHERE BookingId="+ booking.getBookingId());
            return getBooking(organization, booking.getBookingId());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public EventBooking createBooking(Organization organization, Event event, Customer c) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
           ResultSet rs = performQueryWithResult("insert into " + EVENT_BOOKING_TABLE + "(" + EVENT_BOOKING_TABLE_COL_2 + "," + EVENT_BOOKING_TABLE_COL_3 + ","  + EVENT_BOOKING_TABLE_COL_5 +  "," +EVENT_BOOKING_TABLE_COL_6 + "," + EVENT_BOOKING_TABLE_COL_7   + ")" +
                   " values('" + event.getId() + "','" + event.getSelectedAvailability().getId()  + "','" + event.getFinalPrice() + "','" + c.getId() + "','" + System.currentTimeMillis() + "')");
            if (rs.next()) {
                return getBooking(organization,rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List<EventBooking> getAllBookingsForCustomer(Organization organization, Customer c) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        List<EventBooking> list = new ArrayList<>();
        try {
            ResultSet rs = requestData("select * from " + EVENT_BOOKING_TABLE + " where " + EVENT_BOOKING_TABLE_COL_6 + "=" + c.getId());
            while (rs.next()) {
                int eventId = rs.getInt(EVENT_BOOKING_TABLE_COL_2);
                int availabilityId = rs.getInt(EVENT_BOOKING_TABLE_COL_3);
                Event event = eventDataSource.getEvent(organization, new Event(eventId));
                EventAvailability availability = availabilityDataSource.getEventAvailability(organization,new EventAvailability(availabilityId));
                event.addAvailability(availability, availability.getLocation());
                Payment payment = paymentDataSource.getPayment(organization, rs.getInt(EVENT_BOOKING_TABLE_COL_4));
                list.add(new EventBooking(rs.getInt(EVENT_BOOKING_TABLE_COL_1),event,payment, rs.getDouble(EVENT_BOOKING_TABLE_COL_5),null,rs.getInt(EVENT_BOOKING_TABLE_COL_7),rs.getInt(EVENT_BOOKING_TABLE_COL_8)));
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int cancelBooking(Organization organization, EventBooking booking) {
        booking.setStatus(1);
        try {
            EventAvailability availability = booking.getEvent().getSelectedAvailability();
            int resp = availabilityDataSource.updateEventAvailability(organization,availability);
            if (resp == 400) return 302;
            openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
            performQuery("update " + EVENT_BOOKING_TABLE + " set " + EVENT_BOOKING_TABLE_COL_8 + "=" + booking.getStatus() +  " WHERE BookingId="+ booking.getBookingId());
            return 200;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return 400;
    }
}
