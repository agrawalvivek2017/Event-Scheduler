package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    // todo remove
    public User() {}
    
    @JsonProperty("UserId")
    private int id;
    @JsonProperty("UserName")
    private String name;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("UserRole")
    private UserRole userRole;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole= role;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // todo add this when we make apis to change password or create a new user
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean verifyLogin(String username, String password) {
        if (this.password == null || password == null) return false;
        if (this.email == null || username == null) return false;
        return this.password.equals(password) && this.email.equals(username);
    }
}
