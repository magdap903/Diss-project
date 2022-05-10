package com.example.dissertation;

import java.util.ArrayList;

public class Events {

    private String nameOfEvent, dateOfEvent, locationName, eventID;
//    private ArrayList days;

    public Events() {}

    public Events(String nameOfEvent, String dateOfEvent, String locationName, String eventID) {
        this.nameOfEvent = nameOfEvent;
        this.dateOfEvent = dateOfEvent;
        this.locationName = locationName;
        this.eventID = eventID;
//        this.days = days;

    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

//    public ArrayList getDays() {
//        return days;
//    }
//
//    public void setDays(ArrayList days) {
//        this.days = days;
//    }


}
