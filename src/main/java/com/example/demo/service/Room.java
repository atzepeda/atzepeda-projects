package com.example.demo.service;

public class Room {

    //Characteristics of a Room
    private boolean lightStatus;
    private String roomId;

    public Room() {
    }

    //Constructor for Room
    public Room(String id) {
        setLightStatus(false);
        setRoomId(id);
    }

    public boolean isLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(boolean lightStatus) {
        this.lightStatus = lightStatus;
    }

    public void changeLightStatus() { lightStatus = !lightStatus; }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("lightStatus=").append(lightStatus);
        sb.append(", roomId='").append(roomId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
