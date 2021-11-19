package com.events.scheduler.api.organization;

import com.events.scheduler.db.organization.OrganizationServiceDataSourceFactory;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import org.springframework.web.bind.annotation.*;

import static com.events.scheduler.util.Constants.SQL;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationApiImpl implements OrganizationApi {

   OrganizationServiceDataSourceFactory factory =  OrganizationServiceDataSourceFactory.getInstance();
    OrganizationService service = OrganizationService.getInstance(factory.getOrganizationServiceDataSource(SQL));

    @PostMapping
    @ResponseBody
    @Override
    public Response createOrganization(@RequestBody Organization organization) {
        // pass name and key only
        if(organization != null  && organization.verifyDetails()) {

           int rsp = service.addOrganization(organization);
           if (rsp > 1000) {
              return getOrganization(rsp);
           } else if(rsp == 200) {
                Organization org = service.getOrganization(organization.getOrganizationName());
                if (org == null) {
                    return new Response<Organization>(304, "Some error occurred while creating organization");
                }
                return new Response<Organization>(org, 200);
            }
            else if (rsp == 400) {
                return new Response<Organization>(rsp, "Invalid data");
            } else {
                return new Response<Organization>(rsp, "Unauthorized");
            }
        }
        return new Response<Organization>(305, "Invalid key provided");
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    @Override
    public Response getOrganization(@PathVariable int orgId) {
        Organization orgToGet = new Organization();
        orgToGet.setOrgId(orgId);
        Organization org = service.getOrg(orgToGet);
        if (org == null) {
            return new Response<Organization>(304, "Some error occurred while retrieving organization");
        }
        return new Response<Organization>(org, 200);
    }

}
