package com.example.dissertation;

import java.util.ArrayList;

public class Events {

    private String nameOfEvent, dateOfEvent, locationName, lat_lng, formOfVolunteering, typeOfVolunteering,
            eventID, ageMin, ageMax;
    private ArrayList<String> daysOfTheWeek;

    public Events() {}

    public Events(String nameOfEvent, String dateOfEvent, String locationName, String lat_lng, String typeOfVolunteering,
                  String formOfVolunteering, String eventID, String ageMin, String ageMax, ArrayList<String> daysOfTheWeek) {
        this.nameOfEvent = nameOfEvent;
        this.dateOfEvent = dateOfEvent;
        this.locationName = locationName;
        this.lat_lng = lat_lng;
        this.typeOfVolunteering = typeOfVolunteering;
        this.formOfVolunteering = formOfVolunteering;
        this.eventID = eventID;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.daysOfTheWeek = daysOfTheWeek;

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

    public String getTypeOfVolunteering() {
        return typeOfVolunteering;
    }

    public void setTypeOfVolunteering(String typeOfVolunteering) {
        this.typeOfVolunteering = typeOfVolunteering;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(String ageMin) {
        this.ageMin = ageMin;
    }

    public String getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(String ageMax) {
        this.ageMax = ageMax;
    }

    public ArrayList<String> getDaysOfTheWeek() {
        return daysOfTheWeek;
    }

    public void setDaysOfTheWeek(ArrayList<String> daysOfTheWeek) {
        this.daysOfTheWeek = daysOfTheWeek;
    }


}
