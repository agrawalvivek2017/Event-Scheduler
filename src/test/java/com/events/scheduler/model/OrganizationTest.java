package com.events.scheduler.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class OrganizationTest {

    Organization org;
    @Before
    public void setup() {
        org = new Organization(5,"orgDbName", "organizationName", "AB1234", new Date());
    }

    //pass the value of OrgKey which is already present
    @Test
    public void testVerifyDetailsSuccess() {
        Assert.assertTrue(org.verifyDetails());
    }

    //pass the value of OrgKey which is not there in database
    @Test
    public void testVerifyDetailsFail() {
        org = new Organization(5,"orgDbName", "organizationName", "orgKey", new Date());
        Assert.assertFalse(org.verifyDetails());
    }

    @Test
    public void testVerifyDetailsFailWithoutOrgname() {
        org = new Organization(5,null, null, "AB1234", new Date());
        Assert.assertFalse(org.verifyDetails());
    }


}