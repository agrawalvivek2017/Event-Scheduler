/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.events.scheduler.api.user.customer;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import com.events.scheduler.model.Customer;
import com.events.scheduler.model.User;
import com.events.scheduler.model.UserRole;

/**
 *
 * @author vivekagrawal
 */
public interface CustomerApi {
    Response searchCustomer(String orgName, String keyword);
    Response getAllCustomer(String orgName);
    Response createCustomer(String orgName, Customer customer);
    Response getCustomer(String orgName, int id);
    Response updateCustomer(String orgName, int id, Customer customer);
    Response loginCustomer(String orgName, String userName, String password);
}


