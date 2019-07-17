package com.example.demo.controller;

import com.example.demo.service.MiddleManService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/addRoom/{roomID}")
    public ResponseEntity<String> addRoom(@PathVariable String roomID) {
        LOGGER.debug("called add Room");
        middleManService.addRoom(roomID);
        return new ResponseEntity<>("it worked", HttpStatus.OK);
    }
}
