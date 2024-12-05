package com.beulah.bookmanagementservice.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    private int id;

    @ApiModelProperty(
            notes = "Book's title",
            example = "The Great Gatsby",
            required = true,
            position = 1
    )
    @NotBlank(message = "Book must have a provided title")
    private String title;

    @ApiModelProperty(
            notes = "Book's ISBN (unique identifier)",
            example = "978-0743273565",
            required = true,
            position = 2
    )
    @NotBlank(message = "Book must have a provided isbn")
    private String isbn;

    @ApiModelProperty(
            notes = "Book's publication date in YYYY-MM-DD format",
            example = "1925-04-10",
            required = true,
            position = 3
    )
    @NotBlank(message = "Book must have a provided publishDate")
    private String publishDate;

    @ApiModelProperty(
            notes = "Book's price in cents",
            example = "2999",
            required = true,
            position = 4
    )
    @NotNull(message = "Book must have a provided price")
    @PositiveOrZero(message = "Price must be a non-negative value")
    private Integer price;

    @ApiModelProperty(
            notes = "Book's type (HARD_COVER, SOFT_COVER, E_BOOK, AUDIO_BOOK)",
            example = "HARD_COVER",
            required = true,
            position = 5
    )
    @NotBlank(message = "Book must have a provided bookType")
    private String bookType;

    @ApiModelProperty(
            notes = "Book's author",
            example = "F. Scott Fitzgerald",
            required = true,
            position = 6
    )
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
