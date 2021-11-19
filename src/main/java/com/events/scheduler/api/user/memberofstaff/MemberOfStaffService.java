package com.events.scheduler.api.user.memberOfStaff;

import com.events.scheduler.db.user.memberOfStaff.MemberOfStaffDataSource;
import com.events.scheduler.db.user.memberOfStaff.MemberOfStaffSQLDataSource;
import com.events.scheduler.model.MemberOfStaff;
import com.events.scheduler.model.Organization;


import java.util.List;

public class MemberOfStaffService {
    MemberOfStaffDataSource memberOfStaffDataSource;

    private static MemberOfStaffService INSTANCE = null;

    private MemberOfStaffService() {
    }
    
    private MemberOfStaffService(MemberOfStaffDataSource source) {
        memberOfStaffDataSource = source;
    }

    public static MemberOfStaffService getInstance(MemberOfStaffDataSource source) {
        if (INSTANCE == null) {
            INSTANCE = new MemberOfStaffService(source);
        }
        return INSTANCE;
    }

    public static MemberOfStaffService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberOfStaffService();
        }
        return INSTANCE;
    }

    List<MemberOfStaff> searchMemberOfStaff(Organization org, String keyword) {
        return memberOfStaffDataSource.searchMemberOfStaff(org, keyword);
    }

    List<MemberOfStaff> getAllMemberofStaffs(Organization org) {
        return memberOfStaffDataSource.getAllMemberOfStaff(org);
    }

    int createMemberOfStaff(Organization org, MemberOfStaff MemberOfStaff) {
        return memberOfStaffDataSource.createMemberOfStaff(org, MemberOfStaff);
    }

    int updateMemberOfStaff(Organization org, MemberOfStaff MemberOfStaff) {
        return memberOfStaffDataSource.updateMemberOfStaff(org, MemberOfStaff);
    }

    public MemberOfStaff getMemberOfStaff(Organization org, MemberOfStaff member) {
        return memberOfStaffDataSource.getMemberOfStaff(org, member);
    }
    public MemberOfStaff getMemberOfStaff(Organization org, int staffId) {
        return memberOfStaffDataSource.getMemberOfStaff(org, staffId);
    }
    
    public MemberOfStaff loginMemberOfStaff(Organization org, String userName, String password) {
        return memberOfStaffDataSource.loginMemberOfStaff(org, userName, password);
    }
}
