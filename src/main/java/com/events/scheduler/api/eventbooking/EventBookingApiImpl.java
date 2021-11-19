package com.events.scheduler.api.eventbooking;

import com.events.scheduler.api.events.EventsService;
import com.events.scheduler.api.organization.OrganizationService;
import com.events.scheduler.api.user.customer.CustomerService;
import com.events.scheduler.db.eventavailability.EventAvailabilityDataSource;
import com.events.scheduler.db.eventavailability.EventAvailabilityDataSourceFactory;
import com.events.scheduler.db.eventbooking.EventBookingDataSource;
import com.events.scheduler.db.eventbooking.EventBookingDataSourceFactory;
import com.events.scheduler.db.events.EventDataSourceFactory;
import com.events.scheduler.db.organization.OrganizationServiceDataSourceFactory;
import com.events.scheduler.db.payment.PaymentDataSourceFactory;
import com.events.scheduler.db.user.customer.CustomerDataSourceFactory;
import com.events.scheduler.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.events.scheduler.util.Constants.SQL;

@RestController
public class EventBookingApiImpl  implements  EventBookingApi{

    private OrganizationServiceDataSourceFactory orgFactory = OrganizationServiceDataSourceFactory.getInstance();
    private OrganizationService organizationService = OrganizationService.getInstance(orgFactory.getOrganizationServiceDataSource(SQL));
    private EventDataSourceFactory eventDataSourceFactory = EventDataSourceFactory.getInstance();
    private EventsService eventsService = EventsService.getInstance(eventDataSourceFactory.getEventDataSource(SQL), EventAvailabilityDataSourceFactory.getInstance().getEventAvailabilityDataSource(SQL));
    private EventBookingDataSourceFactory eventBookingDataSourceFactory = EventBookingDataSourceFactory.getInstance();
    private EventAvailabilityDataSource eventAvailabilityDataSource = EventAvailabilityDataSourceFactory.getInstance().getEventAvailabilityDataSource(SQL);
    private EventBookingDataSource eventBookingDataSource = eventBookingDataSourceFactory.getEventBookingDataSource(SQL,eventDataSourceFactory.getEventDataSource(SQL),eventAvailabilityDataSource, PaymentDataSourceFactory.getInstance().getPaymentDataSource(SQL));
    private EventBookingService eventBookingService = EventBookingService.getInstance(eventAvailabilityDataSource,eventBookingDataSource, PaymentDataSourceFactory.getInstance().getPaymentDataSource(SQL));
    private CustomerService customerService = CustomerService.getInstance(CustomerDataSourceFactory.getInstance().getCustomerDataSource(SQL));


    @RequestMapping(value = "{orgName}/events/{eventId}/availability/{availabilityId}/book", method = RequestMethod.POST)
    @Override
    public Response createBooking(@PathVariable String orgName, @RequestHeader(name = "userId") int customerId,@PathVariable int eventId,@PathVariable int availabilityId,@RequestBody Map<String,String> data) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(eventId == 0 || availabilityId == 0) {
            return new Response(304, "Invalid event");
        }
        if(customerId == 0) {
            return new Response(401, "UnAuthorized");
        }
        if (data == null || data.size() == 0 || !data.containsKey("location"))
            return new Response(306, "Invalid data");
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }

        Customer c = customerService.getCustomer(org, customerId);
        if (c == null) {
            return new Response(401, "Unauthorized");
        }
        Event eventWithAvailability = eventsService.getEventWithAvailability(org, eventId,availabilityId, data.get("location"));
        if (eventWithAvailability.isValid()) {
            EventBooking booking = eventBookingService.createBooking(org, eventWithAvailability, c);
            if (booking != null) {
                return new Response<EventBooking>(booking, 200);
            }
        }
        return new Response(400, "Some error occurred. Unable to create a booking");
    }

    @RequestMapping(value = "{orgName}/booking/{bookingId}", method = RequestMethod.PUT)
    @Override
    public Response updateBooking(@PathVariable String orgName, @RequestHeader(name = "userId") int customerId,@PathVariable int bookingId,@RequestHeader("availabilityId") int availabilityId,@RequestBody Map<String,String> data) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(bookingId == 0 || availabilityId == 0) {
            return new Response(304, "Invalid event");
        }
        if(customerId == 0) {
            return new Response(401, "UnAuthorized");
        }
        if (data == null || data.size() == 0 || !data.containsKey("location"))
            return new Response(306, "Invalid data");
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Customer c = customerService.getCustomer(org, customerId);
        if (c == null) {
            return new Response(401, "Unauthorized");
        }
        EventBooking existingBooking = eventBookingService.getBooking(org, bookingId);
        if (existingBooking == null) {
            return new Response(302, "Invalid Booking");
        }
        Event eventWithAvailability = eventsService.getEventWithAvailability(org, existingBooking.getEvent().getId(),availabilityId,data.get("location"));
        if (eventWithAvailability.isValid()) {
            EventBooking booking = eventBookingService.updateBooking(org, existingBooking,eventWithAvailability, c);
            if (booking != null) {
                return new Response<EventBooking>(booking, 200);
            }
        }
        return new Response(400, "Some error occurred. Unable to modify the booking");
    }

    @RequestMapping(value = "{orgName}/booking/{bookingId}", method = RequestMethod.DELETE)
    @Override
    public Response cancelBooking(@PathVariable String orgName, @RequestHeader(name = "userId") int customerId,@PathVariable int bookingId) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(bookingId == 0 ) {
            return new Response(304, "Invalid event");
        }
        if(customerId == 0) {
            return new Response(401, "UnAuthorized");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Customer c = customerService.getCustomer(org, customerId);
        if (c == null) {
            return new Response(401, "Unauthorized");
        }
        EventBooking booking = eventBookingService.getBooking(org, bookingId);
        if (booking.verifyCustomer(customerId)) {
            int rsp = eventBookingService.cancelBooking(org, booking);
            if (rsp == 200) {
                return new Response(200, "Booking cancelled successful");
            } else {
                return new Response(rsp, "Transaction failed");
            }
        }
        return new Response(403, "Some error occurred. Unable to cancel the booking");
    }

    @RequestMapping(value = "{orgName}/booking/{bookingId}/payment", method = RequestMethod.POST)
    @Override
    public Response makePayment(@PathVariable String orgName,@PathVariable int bookingId, @RequestHeader(name = "userId") int customerId,@RequestBody PaymentDetails details) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(bookingId == 0) {
            return new Response(304, "Invalid booking");
        }
        if(customerId == 0) {
            return new Response(401, "UnAuthorized");
        }
        if (details == null || details.getAmount() <= 0.0 || details.getType() == null)
            return new Response(306, "Invalid data");
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Customer c = customerService.getCustomer(org, customerId);
        if (c == null) {
            return new Response(401, "Unauthorized");
        }
        EventBooking booking = eventBookingService.getBooking(org, bookingId);
        if (booking != null && booking.verifyCustomer(customerId)) {
                EventBooking modifiedBooking = eventBookingService.modifyBooking(org,booking,details.getType(),details.getAmount());
                if (modifiedBooking == null) {
                    return new Response(403, "Some error occurred. Unable to complete payment.");
                } else {
                    return new Response(modifiedBooking,200);
                }
        }

        return new Response(400, "Some error occurred. Unable to proceed with payment");
    }


    @RequestMapping(value = "{orgName}/booking/{bookingId}", method = RequestMethod.GET)
    @Override
    public Response getBooking(@PathVariable String orgName, @RequestHeader(name = "userId") int customerId,@PathVariable int bookingId) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(customerId == 0) {
            return new Response(401, "UnAuthorized");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        Customer c = customerService.getCustomer(org, customerId);
        if (c == null) {
            return new Response(401, "Unauthorized");
        }
        EventBooking booking = eventBookingService.getBooking(org, bookingId);
        if (booking != null) {
            return new Response<EventBooking>(booking, 200);
        }
        return new Response(400, "Some error occurred. Unable to create a booking");
    }


}
