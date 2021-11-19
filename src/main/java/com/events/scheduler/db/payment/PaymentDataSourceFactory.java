package com.events.scheduler.db.payment;

import static com.events.scheduler.util.Constants.SQL;

public final class PaymentDataSourceFactory {

    private PaymentDataSourceFactory() {

    }

    private static PaymentDataSourceFactory INSTANCE = null;

    public synchronized static PaymentDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaymentDataSourceFactory();
        }
        return INSTANCE;
    }

    public PaymentDataSource getPaymentDataSource(String type) {
        if(type.equalsIgnoreCase(SQL)) {
            return new PaymentSQLDataSource();
        }
        return null;
    }
}
