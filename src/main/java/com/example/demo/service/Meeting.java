package com.example.demo.service;

public class Meeting {

    private String meetingID;
    private String date;
    private String time;
    private String room;

    public Meeting() {
        this.setMeetingID("");
        this.setDate("");
        this.setTime("");
        this.setRoom("");
    }

    public Meeting(String id) {
        this.setMeetingID(id);
        this.setDate("");
        this.setTime("");
        this.setRoom("");
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Meeting{");
        sb.append("meetingID='").append(meetingID).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", room='").append(room).append('\'');
        sb.append('}');
        return sb.toString();
    }

    //*****I need to figure out how to easily construct getters and setters******
}
