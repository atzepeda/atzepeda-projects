package com.example.demo;

Public interface MiddleMan {
    @RequestLine("GET /start")
    String getRooms();

    @RequestLine("POST /add/{room}")
    String addRoom(@Param("room") String room);

    @RequestLine("PUT /update/{room}")
    String updateRoom(@Param("room") String room);

    @RequestLine("DELETE /delete/{{room}")
    String deleteRoom(@Param("room") String room);
}

public class MyApp {
    pbulic static void main(String[] args) {
            MiddleMan middleMan = Feign.builder().decoder(new GsonDecoder()).target(GitHub.class, "https://api.github.com");

            System.out.println(middleMan.getRooms());
    }
}
