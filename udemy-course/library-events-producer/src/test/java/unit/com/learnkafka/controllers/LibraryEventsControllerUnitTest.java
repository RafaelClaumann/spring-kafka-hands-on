package com.learnkafka.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.domain.Book;
import com.learnkafka.domain.LibraryEvent;
import com.learnkafka.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
class LibraryEventsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryEventProducer libraryEventProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void postLibraryEvent() throws Exception {
        final Book book = Book.builder().bookId(456).bookName("kafka book").bookAuthor("rafael claumann").build();
        final LibraryEvent libraryEvent = LibraryEvent.builder().libraryEventId(1).book(book).build();
        final String eventString = objectMapper.writeValueAsString(libraryEvent);

        doNothing().when(libraryEventProducer).sendLibraryEventWithTopicName(isA(LibraryEvent.class));

        mockMvc.perform(post("/v1/library-event")
                .content(eventString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

    }

}