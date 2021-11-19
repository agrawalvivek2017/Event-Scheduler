package com.events.scheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    @JsonProperty("eventId")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("capacity")
    private int capacity;
    @JsonProperty("price")
    private double price;
    @JsonProperty("eventAvailability")
    private List<EventAvailability> eventAvailability;
    @JsonProperty("Staff")
    private MemberOfStaff staff;

    public Event() {
    }

    public Event(int id) {
        this.id = id;
    }

    public Event(int id, String title, String description, int capacity, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<EventAvailability> getEventAvailability() {
        return eventAvailability;
    }

    public void setEventAvailability(List<EventAvailability> eventAvailability) {
        this.eventAvailability = eventAvailability;
    }

    public MemberOfStaff getStaff() {
        return staff;
    }

    public void setStaff(MemberOfStaff staff) {
        this.staff = staff;
    }

    @JsonIgnore
    public List<EventAvailability> getValidAvailabilities(List<EventAvailability> availabilities) {
        if (availabilities == null) return null;
        long currentTime = System.currentTimeMillis();
        List<EventAvailability> validAvailabilities = new ArrayList<>();
        for (EventAvailability availability : availabilities) {
            if (!availability.isPastAvailability(currentTime)) {
                validAvailabilities.add(availability);
            }
        }
        return validAvailabilities;
    }

    @JsonIgnore
    public EventAvailability getSelectedAvailability() {
        if (getEventAvailability() != null && getEventAvailability().size() > 0) {
            return getEventAvailability().get(0);
        }
        return null;
    }

    public void addAvailability(EventAvailability availability, String location) {
        if (getEventAvailability() == null) {
            setEventAvailability(new ArrayList<>());
        }
        for (EventAvailability ea : getEventAvailability()) {
            if (ea.getId() == availability.getId()) {
                return;
            }
        }
        if (availability.getLocation() == null || !availability.getLocation().equalsIgnoreCase(location))
            availability.setLocation(location);

        getEventAvailability().add(availability);
    }

    @JsonIgnore
    public double getFinalPrice() {
        if (getEventAvailability() == null) {
            return 0.0;
        }
        return getPrice() * getEventAvailability().size();
    }

    @JsonIgnore
    public boolean isValid() {
        return getEventAvailability() != null && getEventAvailability().size() != 0 && getId() != 0 &&
                getSelectedAvailability().getId() != 0 && getSelectedAvailability().getStatus() == 0;
    }
}
