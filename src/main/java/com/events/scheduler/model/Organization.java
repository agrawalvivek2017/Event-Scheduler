package com.events.scheduler.model;

import com.events.scheduler.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {
    @JsonProperty("orgId")
    private int orgId;
    @JsonProperty("orgDbName")
    private String orgDbName;
    @JsonProperty("orgName")
    private String organizationName;
    @JsonProperty("orgKey")
    private String orgKey;
    @JsonProperty("dateCreated")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    public Organization(int orgId, String orgDbName, String organizationName, String orgKey, Date date) {
        this.orgId = orgId;
        this.orgDbName = orgDbName;
        this.organizationName = organizationName;
        this.orgKey = orgKey;
        this.date = date;
    }

    public Organization() {}

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgDbName() {
        return orgDbName;
    }

    public void setOrgDbName(String orgDbName) {
        this.orgDbName = orgDbName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrgKey() {
        return orgKey;
    }

    public void setOrgKey(String orgKey) {
        this.orgKey = orgKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean verifyDetails() {
        return this.getOrgKey() != null && !this.getOrgKey().isEmpty() && this.getOrganizationName() != null &&
                !this.getOrganizationName().isEmpty() && Constants.orgKeys.contains(this.getOrgKey());
    }
}
