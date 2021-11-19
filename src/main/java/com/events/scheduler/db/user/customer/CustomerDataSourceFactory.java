package com.events.scheduler.db.user.customer;

import static com.events.scheduler.util.Constants.SQL;

public final class CustomerDataSourceFactory {
    private CustomerDataSourceFactory() {
    }
    
    private static CustomerDataSourceFactory INSTANCE = null;

    public synchronized static CustomerDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerDataSourceFactory();
        }
        return INSTANCE;
    }

    public CustomerDataSource getCustomerDataSource(String orgDataType) {
        if (orgDataType.equalsIgnoreCase(SQL))
            return new CustomerSQLDataSource();
        return null;
    }
}
