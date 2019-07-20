package io.github.djunicode.attendanceapp.TeacherSide.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WebSendAttendance {
    @SerializedName("students")
    ArrayList<WebStudentsList> students;

    @SerializedName("subject")
    String subject;

    @SerializedName("div")
    String div;

    @SerializedName("room")
    String room;

    @SerializedName("startTime")
    String startTime;

    @SerializedName("endTime")
    String endTime;

    @SerializedName("date")
    String date;

    public WebSendAttendance(ArrayList<WebStudentsList> students, String subject, String div, String room, String startTime, String endTime, String date) {
        this.students = students;
        this.subject = subject;
        this.div = div;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public String getDiv() {
        return div;
    }

    public String getRoom() {
        return room;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }
}
