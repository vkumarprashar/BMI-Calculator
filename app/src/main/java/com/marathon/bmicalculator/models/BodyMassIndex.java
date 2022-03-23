package com.marathon.bmicalculator.models;

public class BodyMassIndex {
    private String height;
    private String weight;
    private String dateTime;
    private String gender;
    private String result;

    public BodyMassIndex() {

    }
    public BodyMassIndex(String height, String weight, String dateTime, String gender, String result) {
        this.height = height;
        this.weight = weight;
        this.dateTime = dateTime;
        this.gender = gender;
        this.result = result;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
