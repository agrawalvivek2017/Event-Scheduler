package com.events.scheduler.db.user.memberOfStaff;

import com.events.scheduler.model.MemberOfStaff;
import com.events.scheduler.model.Organization;

import java.util.List;

public interface MemberOfStaffDataSource {
    List<MemberOfStaff> searchMemberOfStaff(Organization org, String keyword);

    List<MemberOfStaff> getAllMemberOfStaff(Organization org);

    int createMemberOfStaff(Organization org, MemberOfStaff MemberOfStaff);

    MemberOfStaff getMemberOfStaff(Organization org, int staffId);

    MemberOfStaff getMemberOfStaff(Organization org, MemberOfStaff member);

    int updateMemberOfStaff(Organization org, MemberOfStaff MemberOfStaff);

    MemberOfStaff loginMemberOfStaff(Organization org, String userName, String password);
}
