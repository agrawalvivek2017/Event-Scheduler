package com.events.scheduler.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public  class Constants {
    public static final String SQL = "SQL";

    public static final Set<String> orgKeys = new HashSet<String>(Arrays.asList("AB1234", "KY9810", "UT7471",
            "PS9081", "SL4243","IN9099"));
    public static final String SQL_USERNAME = "root";
    public static final String SQL_PWD = "";
    public static final String ROOT_DB = "esp";
    public static final String ROOT_TABLE = "organization_list";
    public static final String ROOT_TABLE_COL_1 = "Id";
    public static final String ROOT_TABLE_COL_2 = "Name";
    public static final String ROOT_TABLE_COL_3 = "DbName";
    public static final String ROOT_TABLE_COL_4 = "SecretKey";
    public static final String ROOT_TABLE_COL_5 = "created_at";


    public static final String EVENTS_TABLE = "Events";
    public static final String EVENTS_TABLE_COL_1 = "EventId";
    public static final String EVENTS_TABLE_COL_2 = "EventName";
    public static final String EVENTS_TABLE_COL_3 = "EventDesc";
    public static final String EVENTS_TABLE_COL_4 = "capacity";
    public static final String EVENTS_TABLE_COL_5 = "Price";
    public static final String EVENTS_TABLE_COL_6 = "MemberOfStaffId";


    public static final String EVENT_SCHEDULE_TABLE = "EventSchedule";
    public static final String EVENT_SCHEDULE_TABLE_COL_1 = "EventScheduleId";
    public static final String EVENT_SCHEDULE_TABLE_COL_2 = "eventId";
    public static final String EVENT_SCHEDULE_TABLE_COL_3 = "location";
    public static final String EVENT_SCHEDULE_TABLE_COL_4 = "startDateTime";
    public static final String EVENT_SCHEDULE_TABLE_COL_5 = "endDateTime";
    public static final String EVENT_SCHEDULE_TABLE_COL_6 = "status";


    public static final String MEMBER_OF_STAFF_TABLE = "MemberOfStaff";
    public static final String MEMBER_OF_STAFF_TABLE_COL_1 = "userId";
    public static final String MEMBER_OF_STAFF_TABLE_COL_2 = "name";
    public static final String MEMBER_OF_STAFF_TABLE_COL_3 = "email";
    public static final String MEMBER_OF_STAFF_TABLE_COL_4 = "password";
    public static final String MEMBER_OF_STAFF_TABLE_COL_5 = "userRole";
    public static final String MEMBER_OF_STAFF_TABLE_COL_6 = "salary";

    public static final String EVENT_BOOKING_TABLE = "EventBooking";
    public static final String EVENT_BOOKING_TABLE_COL_1 = "BookingId";
    public static final String EVENT_BOOKING_TABLE_COL_2 = "EventId";
    public static final String EVENT_BOOKING_TABLE_COL_3 = "ScheduleId";
    public static final String EVENT_BOOKING_TABLE_COL_4 = "PaymentId";
    public static final String EVENT_BOOKING_TABLE_COL_5 = "FinalPrice";
    public static final String EVENT_BOOKING_TABLE_COL_6 = "CustomerId";
    public static final String EVENT_BOOKING_TABLE_COL_7 = "created_at";
    public static final String EVENT_BOOKING_TABLE_COL_8 = "status";

    public static final String PAYMENT_TABLE = "payments";
    public static final String PAYMENTS_COL_1 = "PaymentId";
    public static final String PAYMENTS_COL_2 = "BookingId";
    public static final String PAYMENTS_COL_3 = "amount";
    public static final String PAYMENTS_COL_4 = "mode";
    public static final String PAYMENTS_COL_5 = "time";

    public static final String CUSTOMER_TABLE = "Customer";
    public static final String CUSTOMER_TABLE_COL_1 = "CustomerId";
    public static final String CUSTOMER_TABLE_COL_2 = "Name";
    public static final String CUSTOMER_TABLE_COL_3 = "Email";
    public static final String CUSTOMER_TABLE_COL_4 = "UserName";
    public static final String CUSTOMER_TABLE_COL_5 = "Password";
    public static final String CUSTOMER_TABLE_COL_6 = "UserRole";
    public static final String CUSTOMER_TABLE_COL_7 = "BillAddress";

}
