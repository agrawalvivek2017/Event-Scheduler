/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.events.scheduler.api.user.customer;
import com.events.scheduler.api.organization.OrganizationService;
import com.events.scheduler.db.organization.OrganizationServiceDataSourceFactory;
import com.events.scheduler.db.user.customer.CustomerDataSourceFactory;
import com.events.scheduler.model.Event;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import com.events.scheduler.model.Customer;
import com.events.scheduler.model.User;
import com.events.scheduler.model.UserRole;
import java.util.List;
import static com.events.scheduler.util.Constants.SQL;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author vivekagrawal
 */
@RestController
public class CustomerApiImpl implements CustomerApi{
    
    OrganizationService organizationService = OrganizationService.getInstance(OrganizationServiceDataSourceFactory.getInstance().getOrganizationServiceDataSource(SQL));
    CustomerDataSourceFactory customerFactory = CustomerDataSourceFactory.getInstance();
    CustomerService customerService = CustomerService.getInstance(customerFactory.getCustomerDataSource(SQL));

    
    @RequestMapping(value = "{orgName}/customer/search", method = RequestMethod.GET)
    @Override
    public Response<List<Customer>> searchCustomer(@PathVariable String orgName, @RequestParam(required = false) String keyword) {
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        if(keyword == null) {
            keyword = "";
        }
        List<Customer> customer =  customerService.searchCustomer(org,keyword);
        if(customer == null) {
            return new Response(305, "Invalid request");
        }
        return new Response<List<Customer>>(customer,200);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/customer", method = RequestMethod.GET)
    @Override
    public Response<List<Customer>> getAllCustomer(@PathVariable String orgName) {
        return searchCustomer(orgName, null);    
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/customer", method = RequestMethod.POST)
    @Override
    public Response<Customer> createCustomer(@PathVariable String orgName,@RequestBody Customer customer) {
         System.out.println("Code reached here");
        if (orgName.isEmpty() || customer == null) {
            return new Response<Customer>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }

        int resp = customerService.createCustomer(org,customer);
        if (resp == 200) {
            Customer c = customerService.getCustomer(org, customer.getId());
            return new Response<Customer>( c,200);
        }
        else {
            return new Response<>(resp, "Some error occurred");
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @RequestMapping(value = "{orgName}/customer/{id}", method = RequestMethod.GET)
    @Override
    public Response getCustomer(@PathVariable String orgName,@PathVariable int id) {
         if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if(id == 0) {
            return new Response(304, "Invalid customer");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }

        Customer c = customerService.getCustomer(org,id);
        if(c == null) {
            return new Response<>(306, " No such customer");
        } else {
            return new Response<>(c, 200);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @RequestMapping(value = "{orgName}/customer/{id}", method = RequestMethod.PUT)
    @Override
    public Response updateCustomer(@PathVariable String orgName,@PathVariable int id,@RequestBody Customer customer) {
         System.out.println("Code reached here");
        if (orgName.isEmpty() || customer == null) {
            return new Response<Customer>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }

        Customer c = customerService.getCustomerWithPwd(org,id);
        if (c.verifyLogin(customer.getUserName(),customer.getPassword())) {
            int resp = customerService.updateCustomer(org,customer, id);
            if (resp == 200) {
                Customer updatedCustomer= customerService.getCustomer(org,id);
                if (updatedCustomer == null) {
                    return new Response<>(306, "Unable to find record");
                } else {
                    return new Response<Customer>(updatedCustomer, 200);
                }
            }
        }
            return new Response<Customer>(305, "Some error occurred");
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @RequestMapping(value = "{orgName}/customer/login", method = RequestMethod.GET)
    @Override
    public Response loginCustomer(@PathVariable String orgName, @RequestHeader (value = "userName") String userName,
    @RequestHeader (value = "password") String password) {
        System.out.println("Code reached here");
        if (orgName.isEmpty()) {
            return new Response<Customer>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }

        Customer customer = customerService.loginCustomer(org, userName, password);

        if (customer == null) {
            System.out.println(customer.getName());
            return new Response<>(306, "login failed incorrect username or password");

        } else {
            return new Response<>(customer, 200);
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }
}
