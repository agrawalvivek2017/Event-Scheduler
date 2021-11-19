package com.events.scheduler.api.user.memberOfStaff;

import com.events.scheduler.model.MemberOfStaff;
import com.events.scheduler.model.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface MemberOfStaffApi {
    Response searchMemberOfStaff(String orgName, String keyword, int userId);

    Response getAllMemberOfStaff(String orgName, int userId);

    Response createMemberOfStaff(String orgName, MemberOfStaff member, int userId);

    Response getMemberOfStaff(String orgName, int id, int userId);

    Response updateMemberOfStaff(String orgName, MemberOfStaff memberOfStaff,int userId, String userName, String password);
    
    Response loginMemberOfStaff(String orgName, String userName, String password);
}
