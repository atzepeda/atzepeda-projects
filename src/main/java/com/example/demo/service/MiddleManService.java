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

    interface RoomService {
        @RequestLine("GET /rooms")
        List<Room> getRooms();

        @RequestLine("POST /rooms/{roomID}")
        String addRoom(@Param("roomID") String roomID);

        @RequestLine("PUT /update/{room}")
        String updateRoom(@Param("room") String room);

        @RequestLine("DELETE /delete/{room}")
        String deleteRoom(@Param("room") String room);
    }

    interface MeetingService {
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

    private final RoomService roomService;
    private final MeetingService meetingService;

    public MiddleManService() {
        roomService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(RoomService.class, "http://127.0.0.1:8000");

        meetingService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MeetingService.class, "http://127.0.0.1:7000");

        // TODO: later we need to make the URL for both of these to be dynamic such that
        // when we run locally, it will be set to 127.0.0.1:XXX and when things are deployed
        // to a "real" location then you can swap it out with whatever DNS name it might have
        // e.g. "http://meetings.armando.com", "http://rooms.armando.com"...
    }

    public String doSomethingThatTouchesBothServices() {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = roomService.getRooms();
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
            meetings = meetingService.getMeetings();
            LOGGER.info("result from meeting service: {}", meetings);
        } catch (Exception e) {
            LOGGER.error("error talking to meeting service", e);
        }

        // meaningless, but just combine the responses from both services into a string
        return String.format("rooms: %s, meetings: %s", rooms, meetings);
    }

    public void addRoom(String name) {
        try {
            roomService.addRoom(name);
            LOGGER.info("added room: " + name);
        } catch (Exception e) {
            LOGGER.error("wasn't able to add room", e);
        }
    }

    public void updateMeetingDate(String name, String date) {
        try {
            meetingService.updateDate(name, date);
        } catch (Exception e) {
            LOGGER.error("can't complete");
        }

    }
}
