package com.events.scheduler.db.user.memberOfStaff;

import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_1;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_2;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_3;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_4;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_5;
import static com.events.scheduler.util.Constants.MEMBER_OF_STAFF_TABLE_COL_6;

import static com.events.scheduler.util.Constants.SQL_PWD;
import static com.events.scheduler.util.Constants.SQL_USERNAME;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.model.MemberOfStaff;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.UserRole;


public class MemberOfStaffSQLDataSource extends MySqlDataSource implements MemberOfStaffDataSource {

    @Override
    public List<MemberOfStaff> searchMemberOfStaff(Organization org, String keyword) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet set = requestData("select " + MEMBER_OF_STAFF_TABLE_COL_1 + ", " + MEMBER_OF_STAFF_TABLE_COL_2
                    + ", " + MEMBER_OF_STAFF_TABLE_COL_3 + ", " + MEMBER_OF_STAFF_TABLE_COL_5 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_6
                    + " from " + MEMBER_OF_STAFF_TABLE + " where " + MEMBER_OF_STAFF_TABLE_COL_2
                    + " like \"%" + keyword + "%\"");
            List<MemberOfStaff> memberOfStaff = new ArrayList<>();
            while (set.next()) {
                MemberOfStaff c = new MemberOfStaff(set.getInt(MEMBER_OF_STAFF_TABLE_COL_1), set.getString(MEMBER_OF_STAFF_TABLE_COL_2),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_3), null,/*set.getString(MEMBER_OF_STAFF_TABLE_COL_4),*/
                        UserRole.valueOf(set.getString(MEMBER_OF_STAFF_TABLE_COL_5)), set.getDouble(MEMBER_OF_STAFF_TABLE_COL_6));
                memberOfStaff.add(c);
            }
            return memberOfStaff;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MemberOfStaff> getAllMemberOfStaff(Organization org) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + MEMBER_OF_STAFF_TABLE);
            List<MemberOfStaff> memberOfStaff = new ArrayList<>();
            while (set.next()) {
                MemberOfStaff c = new MemberOfStaff(set.getInt(MEMBER_OF_STAFF_TABLE_COL_1),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_2), set.getString(MEMBER_OF_STAFF_TABLE_COL_3),
                        null,/*set.getString(MEMBER_OF_STAFF_TABLE_COL_4),*/
                        UserRole.valueOf(set.getString(MEMBER_OF_STAFF_TABLE_COL_5)),
                        set.getDouble(MEMBER_OF_STAFF_TABLE_COL_6));
                memberOfStaff.add(c);
            }
            return memberOfStaff;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createMemberOfStaff(Organization org, MemberOfStaff MemberOfStaff) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet rs =performQueryWithResult("insert into " + MEMBER_OF_STAFF_TABLE + "(" + MEMBER_OF_STAFF_TABLE_COL_2 + "," + MEMBER_OF_STAFF_TABLE_COL_3 + ","
                    + MEMBER_OF_STAFF_TABLE_COL_4 + "," + MEMBER_OF_STAFF_TABLE_COL_5 + "," + MEMBER_OF_STAFF_TABLE_COL_6 
                    + ") values('" + MemberOfStaff.getName() + "','" + MemberOfStaff.getEmail() + "','"
                    + MemberOfStaff.getPassword() + "','" + MemberOfStaff.getUserRole()
                    + "','" + MemberOfStaff.getSalary() +"')");
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }



    @Override
    public MemberOfStaff getMemberOfStaff(Organization org, int staffId) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet set = requestData(
                    "select " + MEMBER_OF_STAFF_TABLE_COL_1 + ", " + MEMBER_OF_STAFF_TABLE_COL_2
                    + ", " + MEMBER_OF_STAFF_TABLE_COL_3 + ", " + MEMBER_OF_STAFF_TABLE_COL_5 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_6 + " from " + MEMBER_OF_STAFF_TABLE + " where " + MEMBER_OF_STAFF_TABLE_COL_1 + "=" + staffId);
            while (set.next()) {
                return new MemberOfStaff(set.getInt(MEMBER_OF_STAFF_TABLE_COL_1), set.getString(MEMBER_OF_STAFF_TABLE_COL_2),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_3), null, /*set.getString(MEMBER_OF_STAFF_TABLE_COL_4),*/
                        UserRole.valueOf(set.getString(MEMBER_OF_STAFF_TABLE_COL_5)),
                        set.getDouble(MEMBER_OF_STAFF_TABLE_COL_6));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public MemberOfStaff getMemberOfStaff(Organization org, MemberOfStaff member) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet set = requestData("select " + MEMBER_OF_STAFF_TABLE_COL_1 + ", " + MEMBER_OF_STAFF_TABLE_COL_2
                    + ", " + MEMBER_OF_STAFF_TABLE_COL_3 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_4 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_5 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_6 + " from " + MEMBER_OF_STAFF_TABLE + " where "
                    + MEMBER_OF_STAFF_TABLE_COL_2 + "='" + member.getEmail() + "' and " + MEMBER_OF_STAFF_TABLE_COL_4 + "=" + "'" + member.getPassword() + "'");
            if (set.next()) {
                return new MemberOfStaff(set.getInt(MEMBER_OF_STAFF_TABLE_COL_1),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_2), set.getString(MEMBER_OF_STAFF_TABLE_COL_3),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_4),
                        UserRole.valueOf(set.getString(MEMBER_OF_STAFF_TABLE_COL_5)),
                        set.getDouble(MEMBER_OF_STAFF_TABLE_COL_6));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int updateMemberOfStaff(Organization org,MemberOfStaff memberOfStaff) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            performQuery("update " + MEMBER_OF_STAFF_TABLE + " set " + MEMBER_OF_STAFF_TABLE_COL_2 + "=\"" + memberOfStaff.getName()
                    + "\"," +  MEMBER_OF_STAFF_TABLE_COL_5 + "=\"" + memberOfStaff.getUserRole() + "\","
                    + MEMBER_OF_STAFF_TABLE_COL_6 + "=\"" + memberOfStaff.getSalary() + "\""
                    + " WHERE userId=" + memberOfStaff.getId());
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public MemberOfStaff loginMemberOfStaff(Organization org, String userName, String password) {
        openConnection(SQL_USERNAME, SQL_PWD, org.getOrgDbName());
        try {
            ResultSet set = requestData("select " + MEMBER_OF_STAFF_TABLE_COL_1 + ", " + MEMBER_OF_STAFF_TABLE_COL_2
                    + ", " + MEMBER_OF_STAFF_TABLE_COL_3 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_5 + ", "
                    + MEMBER_OF_STAFF_TABLE_COL_6 + " from " + MEMBER_OF_STAFF_TABLE + " where "
                    + MEMBER_OF_STAFF_TABLE_COL_3 + "='" + userName + "' and " + MEMBER_OF_STAFF_TABLE_COL_4 + "=" + "'" + password + "'");
            if (set.next()) {
                return new MemberOfStaff(set.getInt(MEMBER_OF_STAFF_TABLE_COL_1),
                        set.getString(MEMBER_OF_STAFF_TABLE_COL_2), set.getString(MEMBER_OF_STAFF_TABLE_COL_3),
                        null,
                        UserRole.valueOf(set.getString(MEMBER_OF_STAFF_TABLE_COL_5)),
                        set.getDouble(MEMBER_OF_STAFF_TABLE_COL_6));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    }

}
