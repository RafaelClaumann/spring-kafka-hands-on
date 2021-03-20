package com.learnkafka.controllers;

import com.learnkafka.domain.Book;
import com.learnkafka.domain.LibraryEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"
        , "spring.kafka.admin.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class LibraryEventsControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void postLibraryEvent() {
        // given
        Book book = Book.builder()
                .bookId(456)
                .bookName("kafka book")
                .bookAuthor("rafael claumann")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(1)
                .book(book)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent);

        // when
        ResponseEntity<LibraryEvent> response = testRestTemplate.exchange("/v1/library-event", HttpMethod.POST, request, LibraryEvent.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


}