package com.example.dissertation;

import java.util.ArrayList;

public class Matches {

    private String nameOfEvent, dateOfEvent, locationName, lat_lng, distance, age, ageMin, ageMax,
            formOfVolunteering, eventID, typeOfVolunteering, numberOfVolunteers;
    private ArrayList<String> days;
    private String score;

    public Matches() {}

    public Matches(String nameOfEvent, String dateOfEvent, String locationName, String lat_lng,
                  String distance, String age, String ageMin, String ageMax, String formOfVolunteering,
                   String eventID, String score, ArrayList<String> days, String typeOfVolunteering, String numberOfVolunteers) {
        this.nameOfEvent = nameOfEvent;
        this.dateOfEvent = dateOfEvent;
        this.days = days;
        this.locationName = locationName;
        this.lat_lng = lat_lng;
        this.distance = distance;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.age = age;
        this.formOfVolunteering = formOfVolunteering;
        this.eventID = eventID;
        this.score = score;
        this.typeOfVolunteering = typeOfVolunteering;
        this.numberOfVolunteers = numberOfVolunteers;


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

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
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
        this.distance = distance + "km away";
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getTypeOfVolunteering() {
        return typeOfVolunteering;
    }

    public void setTypeOfVolunteering(String typeOfVolunteering) {
        this.typeOfVolunteering = typeOfVolunteering;
    }

    public String getNumberOfVolunteers() {
        return numberOfVolunteers;
    }

    public void setNumberOfVolunteers() {
        this.numberOfVolunteers = numberOfVolunteers;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }



}
