/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.events.scheduler.api.user.customer;

import com.events.scheduler.db.user.customer.CustomerDataSource;
import com.events.scheduler.db.user.customer.CustomerSQLDataSource;
import com.events.scheduler.model.*;

import java.util.List;
/**
 *
 * @author vivekagrawal
 */
public final class CustomerService {
    CustomerDataSource CustomerDataSource;

    private static CustomerService INSTANCE = null;

     private CustomerService() {
    }
    private CustomerService(CustomerDataSource source) {
        CustomerDataSource = source;
    }

    public static CustomerService getInstance(CustomerDataSource source) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService(source);
        }
        return INSTANCE;
    }

    List<Customer> searchCustomer(Organization organization, String keyword) {
        return CustomerDataSource.searchCustomer(organization, keyword);
    }

    int createCustomer(Organization organization, Customer customer) {
        return CustomerDataSource.createCustomer(organization, customer);
    }

    int updateCustomer(Organization organization, Customer customer, int id) {
        Customer existingCustomer = getCustomer(organization,id);
        if (existingCustomer.verifyLogin(customer.getUserName(),customer.getPassword())==true)
        {
             return CustomerDataSource.updateCustomer(organization, id, customer);
        }
        else{
        return 0;
        }
    }

    public Customer getCustomer(Organization organization, int id) {
        return CustomerDataSource.getCustomer(organization, id);
    }

    public Customer getCustomerWithPwd(Organization organization, int id) {
        return CustomerDataSource.getCustomerWithPwd(organization, id);
    }
        
    public Customer loginCustomer(Organization organization, String userName, String password) {
        return CustomerDataSource.loginCustomer(organization, userName, password);
    

    }

}
