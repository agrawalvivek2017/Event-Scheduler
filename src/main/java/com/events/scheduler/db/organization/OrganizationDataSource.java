package com.events.scheduler.db.organization;

import com.events.scheduler.model.Organization;
// adapter pattern
public interface OrganizationDataSource {
    public int addOrganization(Organization org);
    Organization getOrganization(String orgName);
    Organization getOrganization(Organization org);
}
