package com.example.demo.service;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MiddleManService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddleManService.class);

    interface RoomServiceApi {
        @RequestLine("GET /rooms")
        List<Room> getRooms();

        @RequestLine("GET /rooms/{roomId}")
        Room getRoom(@Param("roomId") String roomId);

        @RequestLine("POST /rooms/{roomId}")
        Room addRoom(@Param("roomId") String roomId, Room room);

        @RequestLine("PUT /update/{roomId}")
        Room updateRoom(@Param("roomId") String roomId, Room room);

        @RequestLine("DELETE /delete/{room}")
        Room deleteRoom(@Param("roomId") String roomId);
    }

    interface MeetingServiceApi {
        @RequestLine("GET /start")
        List<Meeting> getMeetings();

        @RequestLine("POST /add/{meeting}")
        String addMeeting(@Param("meeting") String meeting);

        @RequestLine("PUT /updateDate/{name}/{date}")
        String updateDate(@Param("name") String name, @Param("date") String date);

        @RequestLine("PUT /updateTime/{name}/{time}")
        String updateTime(@Param("name") String name, @Param("time") String time);

        @RequestLine("PUT /updateRoom/{name}/{room}")
        String updateRoom(@Param("name") String name, @Param("room") String room);

        @RequestLine("DELETE /delete/{meetingID}")
        String deleteMeeting(@Param("meetingID") String meetingID);
    }

    private final RoomServiceApi roomServiceApi;
    private final MeetingServiceApi meetingServiceApi;

    public MiddleManService() {
        roomServiceApi = Feign.builder()
                .decoder(new GsonDecoder())
                .target(RoomServiceApi.class, "http://127.0.0.1:8000");

        meetingServiceApi = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MeetingServiceApi.class, "http://127.0.0.1:7000");

        // TODO: later we need to make the URL for both of these to be dynamic such that
        // when we run locally, it will be set to 127.0.0.1:XXX and when things are deployed
        // to a "real" location then you can swap it out with whatever DNS name it might have
        // e.g. "http://meetings.armando.com", "http://rooms.armando.com"...
    }

    public String doSomethingThatTouchesBothServices() {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = roomServiceApi.getRooms();
            LOGGER.info("result from room service: {}", rooms);
        } catch (Exception e) {
            LOGGER.error("error talking to room service", e);
            // TODO: normally we'd let the exception escape this method, but here to point out that
            // you may want to die here and bail or perhaps continue onward with a "default" value
            // that will be okay to use as a back...
        }

        //String meetings = "defaultValueForMeetings";
        List<Meeting> meetings = new ArrayList<>();
        try {
            meetings = meetingServiceApi.getMeetings();
            LOGGER.info("result from meeting service: {}", meetings);
        } catch (Exception e) {
            LOGGER.error("error talking to meeting service", e);
        }

        // meaningless, but just combine the responses from both services into a string
        return String.format("rooms: %s, meetings: %s", rooms, meetings);
    }

    public Room getRoom(String roomId) {
        try {
            LOGGER.info("getting room: " + roomId);
            Room room = roomServiceApi.getRoom(roomId);
            LOGGER.info("fetched room: " + room);
            return room;
        } catch (Exception e) {
            throw new RuntimeException("wasn't able to get room: " + roomId, e);
        }
    }

    public Room addRoom(Room room) {
        try {
            LOGGER.info("creating room: " + room);
            Room createdRoom = roomServiceApi.addRoom(room.getRoomId(), room);
            LOGGER.info("created room: " + createdRoom);
            return createdRoom;
        } catch (Exception e) {
            throw new RuntimeException("wasn't able to add room: " + room, e);
        }
    }

    // update the entire room based on whatever was passed in
    public Room updateRoom(Room room) {
        try {
            LOGGER.info("updating room: {}", room);
            Room updatedRoom = roomServiceApi.updateRoom(room.getRoomId(), room);
            LOGGER.info("updated room: {}", updatedRoom);
            return updatedRoom;
        } catch (Exception e) {
            throw new RuntimeException("wasn't able to update room: " + room, e);
        }
    }

    // alternative way of updating room where the caller doesn't need to provide "the room", just the aspects to change
    // so you can design middle man to have fine tuned endpoints for "updateJustXXX", but internally it will
    // all look the same... fetch the original Room, set whatever value on it you want, then do the updateRoom() call..
    // so its just a convenience rather than requiring the middle-man caller to provide everything each time... depends
    // on how you want to expose middle man operations for "easy of use" or whatever UI you want to sit on top of it...
    public Room updateRoom(String roomId, boolean lightStatus) {
        try {
            LOGGER.info("updating roomId: {}'s lightStatus to: {}", roomId, lightStatus);

            Room room = getRoom(roomId);
            if (room == null) {
                throw new RuntimeException("wasn't able to find room with roomId: " + roomId);
            }

            room.setLightStatus(lightStatus);

            return updateRoom(room);
        } catch (Exception e) {
            throw new RuntimeException("wasn't able to update roomId: " + roomId, e);
        }
    }

    public void updateMeetingDate(String name, String date) {
        try {
            meetingServiceApi.updateDate(name, date);
        } catch (Exception e) {
            LOGGER.error("can't complete");
        }

    }
}
