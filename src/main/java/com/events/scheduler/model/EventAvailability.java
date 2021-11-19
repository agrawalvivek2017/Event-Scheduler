package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.events.scheduler.util.DateUtility.StringToDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventAvailability {

    @JsonProperty("availabilityId")
    private int id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("startDateTime")
    private String startDateTime;
    @JsonProperty("endDateTime")
    private String endDateTime;
    @JsonProperty("status")
    private int status;

    public EventAvailability(int id) {
        this.id = id;
    }

    public EventAvailability(int id, String location, String startDateTime, String endDateTime, int status) {
        this.id = id;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isPastAvailability(long currentTime) {
        long startTime = StringToDate(getStartDateTime()).getTime();
        if (startTime < currentTime) {
            return true;
        }
        return false;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void reset() {
        setStatus(0);
        setLocation(null);
    }
}
