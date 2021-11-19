package com.events.scheduler.model;

import com.events.scheduler.model.Customer;
import com.events.scheduler.model.UserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    Customer customer;
    @Before
    public void setup() {
        customer = new Customer(12,"test", "test@gmail.com", "test12", "pass", UserRole.CUSTOMER, "some address");
    }

    @Test
    public void testVerifyLoginSuccess() {
        String userName = "test12";
        String password = "pass";
        Assert.assertTrue(customer.verifyLogin(userName,password));
    }

    @Test
    public void testVerifyLoginFail() {
        String userName = "test12";
        String password = "pass123";
        Assert.assertFalse(customer.verifyLogin(userName,password));
    }

}