package com.events.scheduler.db.user.memberOfStaff;

import static com.events.scheduler.util.Constants.SQL;

public final class MemberOfStaffDataSourceFactory {
    private MemberOfStaffDataSourceFactory() {
    }
    
    private static MemberOfStaffDataSourceFactory INSTANCE = null;

    public synchronized static MemberOfStaffDataSourceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberOfStaffDataSourceFactory();
        }
        return INSTANCE;
    }

    public MemberOfStaffDataSource getMemberOfStaffDataSource(String orgDataType) {
        if (orgDataType.equalsIgnoreCase(SQL))
            return new MemberOfStaffSQLDataSource();
        return null;
    }
}
