package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)    
public class MemberOfStaff extends User {    
    @JsonProperty("salary")
    private double salary;

    public MemberOfStaff(String email, String password) {
        super(email, password);
    }
   
    public MemberOfStaff(int id, String name, String email, String password, UserRole role, double setSalary) {

        super(id, name, email, password, role);
        salary = setSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean verifyLogin(String userName, String password) {
        if (this.getPassword() == null || password == null)
            return false;
        if (this.getEmail() == null || userName == null)
            return false;
        return this.getPassword().equals(password) && this.getName().equals(userName);
    }

    public boolean isAdmin() {
        return this.getUserRole() == UserRole.ADMIN;
    }
}
