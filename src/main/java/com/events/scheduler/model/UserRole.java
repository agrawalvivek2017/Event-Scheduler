package com.events.scheduler.model;

public enum UserRole {
    CUSTOMER("CUSTOMER"), MEMBER_OF_STAFF("MEMBER_OF_STAFF"), ADMIN("ADMIN");
    private final String value;
    private UserRole(String type) {
        this.value = type;
    }
    @Override
    public String toString() {
        return value;
    }
};
