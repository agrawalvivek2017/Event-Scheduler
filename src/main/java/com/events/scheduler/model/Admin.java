package com.events.scheduler.model;

public class Admin extends MemberOfStaff{

    public Admin(int id, String name, String email, String password, UserRole role, double setSalary) {
        super(id, name, email, password, role, setSalary);
    }

    public boolean verifyAdmin(int userId) {
        return this.getId() == userId && this.getUserRole() == UserRole.ADMIN;
    }
}
