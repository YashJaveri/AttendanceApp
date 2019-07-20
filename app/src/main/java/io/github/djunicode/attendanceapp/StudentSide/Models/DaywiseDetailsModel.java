package io.github.djunicode.attendanceapp.StudentSide.Models;

public class DaywiseDetailsModel {
    private String date, time;
    private Boolean status;

    public DaywiseDetailsModel(String date, String time, Boolean status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Boolean getStatus() {
        return status;
    }
}
