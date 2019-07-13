package com.example.demo;


import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

public class MiddleManService {

    public static final String HTTPS_API_GITHUB_COM = "https://api.github.com";

    interface RoomService {
        @RequestLine("GET /start")
        String getRooms();

        @RequestLine("POST /add/{room}")
        String addRoom(@Param("room") String room);

        @RequestLine("PUT /update/{room}")
        String updateRoom(@Param("room") String room);

        @RequestLine("DELETE /delete/{room}")
        String deleteRoom(@Param("room") String room);
    }

    interface MeetingService {
        @RequestLine("GET /start")
        String getMeetings();

        @RequestLine("POST /add/{meeting}")
        String addMeeting(@Param("meeting") String meeting);

        @RequestLine("PUT /update/{meetingID}")
        String updateMeeting(@Param("meetingID") String meetingID);

        @RequestLine("DELETE /delete/{meetingID}")
        String deleteMeeting(@Param("meetingID") String meetingID);
    }

    private final RoomService roomService;
    private final MeetingService meetingService;

    public MiddleManService() {
        roomService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(RoomService.class, HTTPS_API_GITHUB_COM);

        meetingService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MeetingService.class, HTTPS_API_GITHUB_COM);

        System.out.println(roomService.getRooms());
        System.out.println(meetingService.getMeetings());
    }
}
