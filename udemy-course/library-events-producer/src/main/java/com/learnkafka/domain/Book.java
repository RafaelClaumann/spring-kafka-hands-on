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
public class Book implements Serializable {
    private static final long serialVersionUID = -1130026173351245203L;

    private Integer bookID;
    private String bookName;
    private String bookAuthor;

}
