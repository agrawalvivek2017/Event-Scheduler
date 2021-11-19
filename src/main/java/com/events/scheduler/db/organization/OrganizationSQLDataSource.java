package com.events.scheduler.db.organization;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.model.Organization;

import java.sql.ResultSet;

import static com.events.scheduler.util.Constants.*;
import static com.events.scheduler.util.DateUtility.StringToDate;

public class OrganizationSQLDataSource extends MySqlDataSource implements OrganizationDataSource {

    public int addOrganization(Organization org) {
        openConnection(SQL_USERNAME, SQL_PWD, ROOT_DB);
        if(org.getOrganizationName().isEmpty() || org.getOrgKey().isEmpty()) return 400;
        org.setOrgDbName(org.getOrganizationName().trim());
        try {
            ResultSet rs = performQueryWithResult("insert into " + ROOT_TABLE + "(" + ROOT_TABLE_COL_2 + "," + ROOT_TABLE_COL_3 + "," + ROOT_TABLE_COL_4 + ")" +
                    " values('" + org.getOrganizationName() + "','" + org.getOrgDbName() + "','" + org.getOrgKey() + "')");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 401;
    }

    public Organization getOrganization(String orgName) {
        openConnection(SQL_USERNAME,SQL_PWD, ROOT_DB);
        try {
           ResultSet rs = requestData("select * from " + ROOT_TABLE + " where " + ROOT_TABLE_COL_2 + "="+ "'" + orgName +"'");
           while (rs.next()) {
               return new Organization(rs.getInt(ROOT_TABLE_COL_1), rs.getString(ROOT_TABLE_COL_2), rs.getString(ROOT_TABLE_COL_3), rs.getString(ROOT_TABLE_COL_4),StringToDate(rs.getString(ROOT_TABLE_COL_5)));
           }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Organization getOrganization(Organization orgToGet) {
        int id = orgToGet.getOrgId();
        openConnection(SQL_USERNAME,SQL_PWD, ROOT_DB);
        try {
            ResultSet rs = requestData("select * from " + ROOT_TABLE + " where " + ROOT_TABLE_COL_1 + "="+ "'" + id +"'");
            while (rs.next()) {
                return new Organization(rs.getInt(ROOT_TABLE_COL_1), rs.getString(ROOT_TABLE_COL_2), rs.getString(ROOT_TABLE_COL_3), rs.getString(ROOT_TABLE_COL_4),StringToDate(rs.getString(ROOT_TABLE_COL_5)));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

}