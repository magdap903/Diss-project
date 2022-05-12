package com.example.dissertation;

public class Matches {

    private String nameOfEvent, dateOfEvent, locationName, lat_lng, distance, ageMin, ageMax, formOfVolunteering, eventID;
//    private ArrayList days;
    private int score;

    public Matches() {}

    public Matches(String nameOfEvent, String dateOfEvent, String locationName, String lat_lng,
                  String distance, String ageMin, String ageMax, String formOfVolunteering, String eventID, int score) {
        this.nameOfEvent = nameOfEvent;
        this.dateOfEvent = dateOfEvent;
        this.locationName = locationName;
        this.lat_lng = lat_lng;
        this.distance = distance;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.formOfVolunteering = formOfVolunteering;
        this.eventID = eventID;
        this.score = score;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



}
