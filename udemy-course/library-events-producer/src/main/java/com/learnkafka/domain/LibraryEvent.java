package com.learnkafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LibraryEvent implements Serializable {
    private static final long serialVersionUID = 1916591533906813332L;

    private Integer libraryEventId;
    private Book book;
    private LibraryEventType eventType;

}
