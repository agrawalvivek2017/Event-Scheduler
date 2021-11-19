package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
   public enum PaymentMode {
      CREDIT_CARD("CREDIT_CARD"), DEBIT_CARD("DEBIT_CARD");
      private String value;
      private PaymentMode(String v) {
         value = v;
      }

      @Override
      public String toString() {
         return value;
      }

   };

   @JsonProperty("paymentId")
   int paymentId;

   @JsonIgnore
   EventBooking booking;
   @JsonProperty("amount")
   double amount;
   @JsonProperty("paymentType")
   PaymentMode type;
   @JsonProperty("datetime")
   String time;

   public Payment(int paymentId, EventBooking booking, double amount, PaymentMode type, String time) {
      this.paymentId = paymentId;
      this.booking = booking;
      this.amount = amount;
      this.type = type;
      this.time = time;
   }

   public Payment(EventBooking booking, double amount, PaymentMode type) {
      this.booking = booking;
      this.amount = amount;
      this.type = type;
   }

   public int getPaymentId() {
      return paymentId;
   }

   public void setPaymentId(int paymentId) {
      this.paymentId = paymentId;
   }

   public EventBooking getBooking() {
      return booking;
   }

   public void setBooking(EventBooking booking) {
      this.booking = booking;
   }

   public double getAmount() {
      return amount;
   }

   public void setAmount(double amount) {
      this.amount = amount;
   }

   public PaymentMode getType() {
      return type;
   }

   public void setType(PaymentMode type) {
      this.type = type;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

}
