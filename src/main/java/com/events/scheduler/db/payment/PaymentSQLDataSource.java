package com.events.scheduler.db.payment;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Payment;
import com.events.scheduler.util.DateUtility;

import java.sql.ResultSet;

import static com.events.scheduler.util.Constants.*;

public class PaymentSQLDataSource extends MySqlDataSource implements PaymentDataSource {
    @Override
    public Payment addPayment(Organization organization, Payment p) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        ResultSet rs = null;
        try {
            rs = performQueryWithResult("insert into " + PAYMENT_TABLE + "(" + PAYMENTS_COL_2 + "," + PAYMENTS_COL_3 + "," + PAYMENTS_COL_4  + ")" +
                    " values('" + p.getBooking().getBookingId() + "','" + p.getAmount() + "','" + p.getType().toString() + "')");
            if (rs.next()) {
                int paymentId =  rs.getInt(1);
                return getPayment(organization,paymentId);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Payment getPayment(Organization organization, int paymentId) {
        if (paymentId == 0) return null;
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
           ResultSet rs = requestData("select * from " + PAYMENT_TABLE + " where " + PAYMENTS_COL_1+"="+ paymentId);
            if (rs.next()) {
               return new Payment(rs.getInt(PAYMENTS_COL_1), null, rs.getDouble(PAYMENTS_COL_3), Payment.PaymentMode.valueOf(rs.getString(PAYMENTS_COL_4)), rs.getString(PAYMENTS_COL_5));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return null;
    }
}
