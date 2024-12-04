package com.beulah.bookmanagementweb.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class IndexBookResponse {
    private String message;
    private List<Book> data;

}
