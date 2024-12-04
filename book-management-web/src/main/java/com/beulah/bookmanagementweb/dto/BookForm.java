package com.beulah.bookmanagementweb.dto;

import lombok.Data;

@Data
public class BookForm {
    private String title;
    private String isbn;
    private String publishDate;
    private Integer price;
    private String bookType;
    private String author;
}