package com.learnkafka.controllers;

import com.learnkafka.domain.LibraryEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LibraryEventsController {

    @PostMapping("/v1/library-event")
    public ResponseEntity<LibraryEvent> postEvent(@RequestBody LibraryEvent libraryEvent) {

        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
    
}
