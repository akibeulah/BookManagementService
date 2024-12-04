package com.beulah.bookmanagementservice.models;

import com.beulah.bookmanagementservice.enums.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {
    @NotBlank(message = "Book title is required")
    @Column(name = "title", nullable = false)
    @Size(min = 1, message = "Title must not be empty")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Column(name = "isbn", nullable = false, unique = true)
    @Size(min = 1, message = "ISBN must not be empty")
    private String isbn;

    @NotNull(message = "Publish date is required")
    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private int price;

    @NotNull(message = "Book type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "book_type")
    private BookType bookType;

    private String author;
}