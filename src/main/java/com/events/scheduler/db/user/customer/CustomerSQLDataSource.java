/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.events.scheduler.db.user.customer;

import com.events.scheduler.db.MySqlDataSource;
import com.events.scheduler.db.user.customer.CustomerDataSource;
import com.events.scheduler.model.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.events.scheduler.util.Constants.*;

/**
 *
 * @author vivekagrawal
 */
public class CustomerSQLDataSource extends MySqlDataSource implements CustomerDataSource {

    @Override 
    public List<Customer> searchCustomer(Organization organization, String keyword) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + CUSTOMER_TABLE + " where " + CUSTOMER_TABLE_COL_2 + " like \"%"+ keyword + "%\"" );
            List<Customer> customer = new ArrayList<>();
            while (set.next()) {
                Customer c = new Customer(set.getInt(CUSTOMER_TABLE_COL_1),set.getString(CUSTOMER_TABLE_COL_2), set.getString(CUSTOMER_TABLE_COL_3),set.getString(CUSTOMER_TABLE_COL_4),set.getString(CUSTOMER_TABLE_COL_5),UserRole.valueOf(set.getString(CUSTOMER_TABLE_COL_6).toUpperCase()),set.getString(CUSTOMER_TABLE_COL_7));
                customer.add(c);
            }
            return customer;
        }catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Customer> getAllCustomer(Organization organization) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + CUSTOMER_TABLE);
            List<Customer> customer = new ArrayList<>();
            while (set.next()) {
                Customer c = new Customer(set.getInt(CUSTOMER_TABLE_COL_1),set.getString(CUSTOMER_TABLE_COL_2), set.getString(CUSTOMER_TABLE_COL_3),set.getString(CUSTOMER_TABLE_COL_4),set.getString(CUSTOMER_TABLE_COL_5),UserRole.valueOf(set.getString(CUSTOMER_TABLE_COL_6).toUpperCase()),set.getString(CUSTOMER_TABLE_COL_7));
                customer.add(c);
            }
            return customer;
        } catch (Exception ex) {
           System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createCustomer(Organization organization, Customer customer) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            performQuery("insert into " + CUSTOMER_TABLE + "(" + CUSTOMER_TABLE_COL_2 + "," + CUSTOMER_TABLE_COL_3 + "," + CUSTOMER_TABLE_COL_4 + "," + CUSTOMER_TABLE_COL_5 + "," + CUSTOMER_TABLE_COL_6 + "," + CUSTOMER_TABLE_COL_7 + ")" +
                    " values('" + customer.getName() + "','" + customer.getEmail() + "','" + customer.getUserName() + "','" + customer.getPassword()+ "','" + customer.getUserRole()+"','" + customer.getBillingAddress()+"')");
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Customer getCustomer(Organization organization, int id) {
        openConnection(SQL_USERNAME,SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + CUSTOMER_TABLE + " where " + CUSTOMER_TABLE_COL_1 + "="+ id);
            while (set.next()) {
                return new Customer(set.getInt(CUSTOMER_TABLE_COL_1),set.getString(CUSTOMER_TABLE_COL_2), set.getString(CUSTOMER_TABLE_COL_3),set.getString(CUSTOMER_TABLE_COL_4),null,UserRole.valueOf(set.getString(CUSTOMER_TABLE_COL_6).toUpperCase()),set.getString(CUSTOMER_TABLE_COL_7));
                        }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer getCustomerWithPwd(Organization organization, int id) {
        openConnection(SQL_USERNAME,SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select * from " + CUSTOMER_TABLE + " where " + CUSTOMER_TABLE_COL_1 + "="+ id);
            while (set.next()) {
                return new Customer(set.getInt(CUSTOMER_TABLE_COL_1),set.getString(CUSTOMER_TABLE_COL_2), set.getString(CUSTOMER_TABLE_COL_3),set.getString(CUSTOMER_TABLE_COL_4),set.getString(CUSTOMER_TABLE_COL_5),UserRole.valueOf(set.getString(CUSTOMER_TABLE_COL_6).toUpperCase()),set.getString(CUSTOMER_TABLE_COL_7));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        finally {
            closeConnection();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateCustomer(Organization organization, int id, Customer customer) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            performQuery("update " + CUSTOMER_TABLE + " set " + CUSTOMER_TABLE_COL_2 + "=\"" + customer.getName()+ "\","+ CUSTOMER_TABLE_COL_3 + "=\"" + customer.getEmail()+ "\","+ CUSTOMER_TABLE_COL_4 + "=\"" + customer.getUserName()+ "\","+ CUSTOMER_TABLE_COL_5 + "=\"" + customer.getPassword()+ "\","+ CUSTOMER_TABLE_COL_6 + "=\"" + customer.getUserRole()+ "\","+ CUSTOMER_TABLE_COL_7 + "=\"" + customer.getBillingAddress()+ "\"," + " WHERE CustomerId="+ id);
            return 200;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            closeConnection();
        }
        return 400;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    @Override
    public Customer loginCustomer(Organization organization, String userName, String password) {
        openConnection(SQL_USERNAME, SQL_PWD, organization.getOrgDbName());
        try {
            ResultSet set = requestData("select " + CUSTOMER_TABLE_COL_1 + ", " + CUSTOMER_TABLE_COL_2
                    + ", " + CUSTOMER_TABLE_COL_3 + ", " + CUSTOMER_TABLE_COL_4 + ", "
                    + CUSTOMER_TABLE_COL_5 + ", "
                    + CUSTOMER_TABLE_COL_6 + ", "
                    + CUSTOMER_TABLE_COL_7 + " from " + CUSTOMER_TABLE + " where "
                    + CUSTOMER_TABLE_COL_4 + "='" + userName + "' and " + CUSTOMER_TABLE_COL_5 + "=" + "'" + password + "'");
            while (set.next()) {
                return new Customer(set.getInt(CUSTOMER_TABLE_COL_1),set.getString(CUSTOMER_TABLE_COL_2), set.getString(CUSTOMER_TABLE_COL_3),set.getString(CUSTOMER_TABLE_COL_4),null,UserRole.valueOf(set.getString(CUSTOMER_TABLE_COL_6).toUpperCase()),set.getString(CUSTOMER_TABLE_COL_7));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            closeConnection();
        }
        return null;
    
}
}
