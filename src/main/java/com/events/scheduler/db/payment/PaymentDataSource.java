package com.events.scheduler.db.payment;

import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Payment;

public interface PaymentDataSource {
    Payment addPayment(Organization organization,Payment p);
    Payment getPayment(Organization organization, int paymentId);
}
