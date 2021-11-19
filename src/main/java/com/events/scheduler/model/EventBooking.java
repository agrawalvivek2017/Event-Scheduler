package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventBooking {

    @JsonProperty("bookingId")
    int bookingId;
    @JsonProperty("event")
    Event event;

    @JsonIgnore
    Customer customer;
    @JsonProperty("totalPrice")
    double totalPrice;
    @JsonProperty("payment")
    Payment payment;

    @JsonProperty("last_updated_timeInMillis")
    long bookingDateTime;
    @JsonProperty("status")
    int status;

    public EventBooking(int bookingId, Event event,Payment payment, double totalPrice,Customer customer, long bookingDateTime, int status) {
        this.bookingId = bookingId;
        this.event = event;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.bookingDateTime = bookingDateTime;
        this.status = status;
    }

    public EventBooking(Event event, Customer customer, double totalPrice, Payment payment, long bookingDateTime, int status) {
        this.event = event;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.bookingDateTime = bookingDateTime;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public long getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(long bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Payment createPayment(Payment.PaymentMode mode, double amount) {
        if (getStatus() != 2 && amount != getTotalPrice()) {
            Payment p = new Payment(this, amount, mode);
            setPayment(p);
            return p;
        }
        return null;
    }

    boolean isCancelled() {
        return status == 1;
    }

    boolean isPast() {
        long currentTime = System.currentTimeMillis();
        for(EventAvailability availability : event.getEventAvailability()) {
            if (availability.isPastAvailability(currentTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyCustomer(int customerId) {
        return customerId == customer.getId();
    }
}
