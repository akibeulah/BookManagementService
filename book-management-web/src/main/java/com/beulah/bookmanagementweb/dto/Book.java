package com.beulah.bookmanagementweb.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private int id;
    private String title;
    private String author;
    private String bookType;
    private String isbn;
    private int price;
    private String createdAt;
    private String updatedAt;
    private String publishDate;
}
