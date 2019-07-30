package com.example.demo.controller;

import com.example.demo.service.MiddleManService;
import com.example.demo.service.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiddleManController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddleManController.class);

    private final MiddleManService middleManService;

    @Autowired
    public MiddleManController(MiddleManService middleManService) {
        this.middleManService = middleManService;
    }

    @GetMapping("/something")
    public ResponseEntity<String> getSomething() {
        LOGGER.debug("somethin called");
        String something = middleManService.doSomethingThatTouchesBothServices();
        return new ResponseEntity<>(something, HttpStatus.OK);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomId) {
        LOGGER.debug("called getRoom, roomId: {}", roomId);

        Room room = middleManService.getRoom(roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<Room> addRoom(@PathVariable String roomId, @RequestBody Room room) {
        LOGGER.debug("called addRoom, roomId: {}, room: {}", roomId, room);

        if (room == null) {
            throw new IllegalStateException("room not provided in request body");
        }

        room.setRoomId(roomId);

        Room createdRoom = middleManService.addRoom(room);
        return new ResponseEntity<>(createdRoom, HttpStatus.OK);
    }

    // Note: I changed the update here to be /rooms/{roomId} -- the "shape" of how you want to expose the middleman
    // operations "should" follow a pattern of:

    // create = POST /rooms (note, no "id" in the url).. (request body == Room object)
    // read = GET /rooms/{roomId}
    // update = PUT /rooms/{roomId} (request body == Room object)
    // delete = DELETE /rooms/{roomId}

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomId, @RequestBody Room room) {
        LOGGER.debug("called addRoom, roomId: {}, room: {}", roomId, room);

        if (room == null) {
            throw new IllegalStateException("room not provided in request body");
        }

        room.setRoomId(roomId);

        Room createdRoom = middleManService.updateRoom(room);
        return new ResponseEntity<>(createdRoom, HttpStatus.OK);
    }

    @PutMapping("/updateMeetingDate/{name}/{date}")
    public ResponseEntity<String> updateMeetingDate(@PathVariable String name, @PathVariable String date) {
        middleManService.updateMeetingDate(name, date);
        return new ResponseEntity<>("it worked", HttpStatus.OK);
    }

}
