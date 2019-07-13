package com.example.demo;


import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

public class MyApp {

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
    }


    static void main(String[] args) {
        RoomService roomService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(RoomService.class, HTTPS_API_GITHUB_COM);

        MeetingService meetingService = Feign.builder()
                .decoder(new GsonDecoder())
                .target(MeetingService.class, HTTPS_API_GITHUB_COM);

        System.out.println(roomService.getRooms());
    }
}
