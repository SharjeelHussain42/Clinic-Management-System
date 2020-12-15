package com.sharjeelhussain.smd_project;

public class Patient_Dashboard_Model {
    public Patient_Dashboard_Model() {
    }

    public Patient_Dashboard_Model(String description, String medicine) {
        this.description = description;
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    String description,medicine;

}
