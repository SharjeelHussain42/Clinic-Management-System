package com.sharjeelhussain.smd_project;

public class RegistrationModel {
    String full_name;
    String phone;
    String email;
    String password;

    public RegistrationModel() {
    }

    public RegistrationModel(String full_name, String phone, String email, String password) {
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
