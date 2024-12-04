package com.beulah.bookmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBookRequest {
    private int id;
    @NotBlank(message = "Book must have a provided title")
    private String title;
    @NotBlank(message = "Book must have a provided isbn")
    private String isbn;
    @NotBlank(message = "Book must have a provided publishDate")
    private String publishDate;
    @NotNull(message = "Book must have a provided price")
    @PositiveOrZero(message = "Price must be a non-negative value")
    private Integer price;
    @NotBlank(message = "Book must have a provided bookType")
    private String bookType;
    @NotBlank(message = "Book must have a provided author")
    private String author;

    public NewBookRequest(String title, String isbn, String publishDate, int price, String bookType, String author) {
        this.title = title;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
        this.author = author;
    }
}
