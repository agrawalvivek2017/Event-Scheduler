package com.events.scheduler.api.user.memberOfStaff;

import java.util.List;

import com.events.scheduler.api.organization.OrganizationService;
import com.events.scheduler.db.organization.OrganizationServiceDataSourceFactory;
import com.events.scheduler.model.MemberOfStaff;
import com.events.scheduler.model.Organization;
import com.events.scheduler.model.Response;
import static com.events.scheduler.util.Constants.SQL;
import com.events.scheduler.db.user.memberOfStaff.MemberOfStaffDataSourceFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberOfStaffApiImpl implements MemberOfStaffApi {

    OrganizationService organizationService = OrganizationService.getInstance(OrganizationServiceDataSourceFactory.getInstance().getOrganizationServiceDataSource(SQL));
    MemberOfStaffDataSourceFactory memStaffFactory = MemberOfStaffDataSourceFactory.getInstance();
    MemberOfStaffService memberOfStaffService = MemberOfStaffService.getInstance(memStaffFactory.getMemberOfStaffDataSource(SQL));

    @RequestMapping(value = "{orgName}/MemberOfStaff/search", method = RequestMethod.GET)
    @Override
    public Response<List<MemberOfStaff>> searchMemberOfStaff(@PathVariable String orgName,
            @RequestParam(required = false) String keyword, @RequestHeader (value = "userId") int userId) {
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        MemberOfStaff mem = memberOfStaffService.getMemberOfStaff(org, userId);
        //Admin ad = new Admin(mem.getId(), mem.getName(), mem.getEmail(), mem.getPassword(), mem.getUserRole(), mem.getSalary());
        
        if (mem == null || !mem.isAdmin()) {
            return new Response(401, "Unauthorized to get member of staff");
        }
        List<MemberOfStaff> memberOfStaff;
        if (keyword == null) {
            memberOfStaff = memberOfStaffService.getAllMemberofStaffs(org);
        }
        else {
            memberOfStaff = memberOfStaffService.searchMemberOfStaff(org, keyword);
        }
        if (memberOfStaff == null) {
            return new Response(305, "Invalid request");
        }
        return new Response<List<MemberOfStaff>>(memberOfStaff, 200);
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/MemberOfStaff", method = RequestMethod.GET)
    @Override
    public Response<List<MemberOfStaff>> getAllMemberOfStaff(@PathVariable String orgName, @RequestHeader (value = "userId") int userId) {
        return searchMemberOfStaff(orgName, null, userId);
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/MemberOfStaff", method = RequestMethod.POST)
    @Override
    public Response<MemberOfStaff> createMemberOfStaff(@PathVariable String orgName, @RequestBody MemberOfStaff memberOfStaff,
            @RequestHeader(value = "userId") int userId) {
        System.out.println("Code reached here");
        if (orgName.isEmpty() || memberOfStaff == null) {
            return new Response<MemberOfStaff>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        MemberOfStaff mem = memberOfStaffService.getMemberOfStaff(org, userId);
        //Admin ad = new Admin(mem.getId(), mem.getName(), mem.getEmail(), mem.getPassword(), mem.getUserRole(),mem.getSalary());
        if (mem == null || !mem.isAdmin()) {
            return new Response(302, "Unauthorized to get member of staff");
        }
        int resp = memberOfStaffService.createMemberOfStaff(org, memberOfStaff);
        if (resp != 400) {
            MemberOfStaff c = memberOfStaffService.getMemberOfStaff(org, resp);
            if (c == null) {
                return new Response<>(403, "Some error occurred");
            }
            return new Response<MemberOfStaff>(c, 200);
        } else {
            return new Response<>(resp, "Some error occurred");
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/MemberOfStaff/{id}", method = RequestMethod.GET)
    @Override
    public Response getMemberOfStaff(@PathVariable String orgName, @PathVariable int id,
            @RequestHeader(value = "userId") int userId) {
        if (orgName == null) {
            return new Response(302, "Invalid organization");
        }
        if (id == 0) {
            return new Response(304, "Invalid MemberOfStaff");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        MemberOfStaff mem = memberOfStaffService.getMemberOfStaff(org, userId);
        //Admin ad = new Admin(mem.getId(), mem.getName(), mem.getEmail(), mem.getPassword(), mem.getUserRole(),mem.getSalary());
        if (mem == null || !mem.isAdmin()) {
            return new Response(302, "Unauthorized to get member of staff");
        }
        MemberOfStaff c = memberOfStaffService.getMemberOfStaff(org, id);
        if (c == null) {
            return new Response<>(306, " No such MemberOfStaff");
        } else {
            return new Response<>(c, 200);
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/MemberOfStaff/{id}", method = RequestMethod.PUT)
    @Override
    public Response updateMemberOfStaff(@PathVariable String orgName,
            @RequestBody MemberOfStaff memberOfStaff, @PathVariable(value = "userId") int userId,@RequestHeader (value = "userName") String userName,
                                        @RequestHeader (value = "password") String password) {
        if (orgName.isEmpty() || memberOfStaff == null || userName == null || password == null) {
            return new Response<MemberOfStaff>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        MemberOfStaff mem = memberOfStaffService.getMemberOfStaff(org, new MemberOfStaff(userName,password));
        //Admin ad = new Admin(mem.getId(), mem.getName(), mem.getEmail(), mem.getPassword(), mem.getUserRole(),mem.getSalary());
        if (mem == null || (!mem.isAdmin() && mem.getId() !=userId)) {
            return new Response(401, "Unauthorized to get member of staff");
        }
        if (mem.isAdmin()) {
            mem.setSalary(memberOfStaff.getSalary());
            mem.setUserRole(memberOfStaff.getUserRole());
        }
        mem.setName(memberOfStaff.getName());
        int resp = memberOfStaffService.updateMemberOfStaff(org, memberOfStaff);
        if (resp == 200) {
            MemberOfStaff c = memberOfStaffService.getMemberOfStaff(org,mem);
            if (c == null) {
                return new Response<>(306, "Unable to find record");
            }
            return new Response<MemberOfStaff>(c, 200);
        } else {
            return new Response<MemberOfStaff>(305, "Some error occurred");
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @RequestMapping(value = "{orgName}/MemberOfStaff/login", method = RequestMethod.GET)
    @Override
    public Response loginMemberOfStaff(@PathVariable String orgName, @RequestHeader (value = "userName") String userName,
    @RequestHeader (value = "password") String password) {
        if (orgName.isEmpty()) {
            return new Response<MemberOfStaff>(304, "Invalid input params");
        }
        Organization org = organizationService.getOrganization(orgName);
        if (org == null) {
            return new Response(302, "Invalid organization");
        }
        MemberOfStaff mem = memberOfStaffService.loginMemberOfStaff(org, userName, password);
        System.out.println(mem.getName());
        if (mem == null) {
            return new Response<>(306, "login failed incorrect username or password");
        } else {
            return new Response<MemberOfStaff>(mem, 200);
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }
}
