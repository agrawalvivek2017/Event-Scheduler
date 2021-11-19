/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.events.scheduler.db.user.customer;
import com.events.scheduler.model.Customer;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.User;
import java.util.List;
/**
 *
 * @author vivekagrawal
 */
public interface CustomerDataSource {
    List<Customer> searchCustomer(Organization organization, String keyword);
    List<Customer> getAllCustomer(Organization organization);
    int createCustomer(Organization organization, Customer customer);
    Customer getCustomer(Organization organization, int id);
    Customer getCustomerWithPwd(Organization organization, int id);
    int updateCustomer(Organization organization, int id, Customer customer);
    Customer loginCustomer(Organization organization, String userName, String password);
}
