package com.events.scheduler.db.organization;

import static com.events.scheduler.util.Constants.SQL;

public final class OrganizationServiceDataSourceFactory {

    private OrganizationServiceDataSourceFactory() {

    }

    private static OrganizationServiceDataSourceFactory INSTANCE = null;

    public synchronized static OrganizationServiceDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrganizationServiceDataSourceFactory();
        }
        return INSTANCE;
    }

    public OrganizationDataSource getOrganizationServiceDataSource(String orgDataType) {
        if (orgDataType.equalsIgnoreCase(SQL))
            return new OrganizationSQLDataSource();
        return null;
    }

}
