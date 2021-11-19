package com.events.scheduler.api.organization;

import com.events.scheduler.api.BaseApi;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import org.springframework.web.bind.annotation.*;

public interface OrganizationApi extends BaseApi {
    @PostMapping
    @ResponseBody
    public Response createOrganization(@RequestBody Organization organization);
    public Response getOrganization(int id);

}
