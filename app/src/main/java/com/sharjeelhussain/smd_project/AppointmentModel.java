package com.sharjeelhussain.smd_project;

public class AppointmentModel {
    String PtName;
    String phone;
    String DrEmail;
    String PtEmail;
    String Timing;

    public AppointmentModel(String ptName, String phone, String drEmail, String ptEmail, String timing, String problem, String date, String drName) {
        PtName = ptName;
        this.phone = phone;
        DrEmail = drEmail;
        PtEmail = ptEmail;
        Timing = timing;
        Problem = problem;
        Date = date;
        DrName = drName;
    }

    String Problem;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String Date;





    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    String DrName;

    public AppointmentModel() {
    }





    public String getPtName() {
        return PtName;
    }

    public void setPtName(String ptName) {
        this.PtName = ptName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDrEmail() {
        return DrEmail;
    }

    public void setDrEmail(String drEmail) {
        DrEmail = drEmail;
    }

    public String getPtEmail() {
        return PtEmail;
    }

    public void setPtEmail(String ptEmail) {
        PtEmail = ptEmail;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }
}
