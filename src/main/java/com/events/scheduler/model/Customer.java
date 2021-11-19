package com.events.scheduler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends User{

    
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("billingAddress")
    private String billingAddress;
    

    public Customer(int id){
        this.setId(id);
    }

    public Customer( int id, String name, String email, String userName, String password, UserRole role, String billingAddress) {
        super(id, name, email, password, role);
        this.userName = userName;
        this.billingAddress = billingAddress;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
   public boolean verifyLogin(String userName, String password) {
        if (this.userName == null || userName == null) return false;
        if (this.getPassword() == null || password == null) return  false;
        return this.getPassword().equals(password) && (this.userName.equals(userName) || userName.equals(getEmail()));
   }

}
