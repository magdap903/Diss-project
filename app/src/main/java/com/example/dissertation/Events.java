package com.example.dissertation;

import java.util.ArrayList;

public class Events {

    private String nameOfEvent, dateOfEvent, locationName, lat_lng, formOfVolunteering, eventID;
//    private ArrayList days;

    public Events() {}

    public Events(String nameOfEvent, String dateOfEvent, String locationName, String lat_lng,
                  String formOfVolunteering, String eventID) {
        this.nameOfEvent = nameOfEvent;
        this.dateOfEvent = dateOfEvent;
        this.locationName = locationName;
        this.lat_lng = lat_lng;
        this.formOfVolunteering = formOfVolunteering;
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

    public String getLat_lng() {
        return lat_lng;
    }

    public void setLat_lng(String lat_lng) {
        this.lat_lng = lat_lng;
    }

    public String getFormOfVolunteering() {
        return formOfVolunteering;
    }

    public void setFormOfVolunteering(String formOfVolunteering) {
        this.formOfVolunteering = formOfVolunteering;
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
