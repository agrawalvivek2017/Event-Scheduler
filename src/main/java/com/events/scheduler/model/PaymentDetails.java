package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDetails {
    @JsonProperty("amount")
    double amount;
    @JsonProperty("paymentType")
    Payment.PaymentMode type;

    public PaymentDetails(double amount, Payment.PaymentMode type) {
        this.amount = amount;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payment.PaymentMode getType() {
        return type;
    }

    public void setType(Payment.PaymentMode type) {
        this.type = type;
    }
}
