package com.events.scheduler.api.organization;

import com.events.scheduler.api.BaseService;
import com.events.scheduler.db.DataRepository;
import com.events.scheduler.db.organization.OrganizationDataSource;
import com.events.scheduler.db.organization.OrganizationSQLDataSource;
import com.events.scheduler.model.Organization;

public class OrganizationService implements BaseService {

    private OrganizationDataSource dataSource = null;
    private static OrganizationService INSTANCE = null;

    private OrganizationService(){}
    private OrganizationService(OrganizationDataSource source) {
        dataSource = source;
    }

    public synchronized static OrganizationService getInstance(OrganizationDataSource source) {
       if (INSTANCE == null) {
           INSTANCE = new OrganizationService(source);
       }
       return INSTANCE;
    }

    @Override
    public DataRepository getDataRepository() {
       return (DataRepository) dataSource;
    }

    public OrganizationDataSource getDataSource() {
        return dataSource;
    }

    public int addOrganization(Organization org) {
       return dataSource.addOrganization(org);
    }

    public Organization getOrganization(String orgName) {
        return dataSource.getOrganization(orgName);
    }

    public Organization getOrg(Organization organization) {
        return dataSource.getOrganization(organization);
    }
}
