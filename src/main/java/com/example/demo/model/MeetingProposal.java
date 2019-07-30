package com.example.demo.model;

public class MeetingProposal {
    private String roomId;
    private long meetingTime;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public long getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(long meetingTime) {
        this.meetingTime = meetingTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeetingProposal{");
        sb.append("roomId='").append(roomId).append('\'');
        sb.append(", meetingTime=").append(meetingTime);
        sb.append('}');
        return sb.toString();
    }
}
