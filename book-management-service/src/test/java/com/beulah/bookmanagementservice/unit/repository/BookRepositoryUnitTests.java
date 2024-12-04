package com.beulah.bookmanagementservice.unit.repository;

import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryUnitTests {
    @Autowired
    private BookRepository bookRepository;

    private static Book book;

    @BeforeAll
    public static void setup() {
        book = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .bookType(BookType.SOFT_COVER)
                .price(5274)
                .publishDate(LocalDate.now())
                .build();
    }

    @Test
    @Order(1)
    public void shouldCreateNewBookWithValidData() {
        Book newBook = Book.builder()
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .price(book.getPrice())
                .bookType(book.getBookType())
                .author(book.getAuthor())
                .build();

        Book savedBook = bookRepository.save(newBook);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void shouldFailToCreateNewBookWithInvalidData() {
        Book newBook = Book.builder()
                .publishDate(LocalDate.now())
                .price(120000)
                .bookType(BookType.SOFT_COVER)
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            bookRepository.save(newBook);
        });
    }

    @Test
    @Order(3)
    public void shouldUpdateBookWithValidData() {
        Book savedBook = bookRepository.save(book);

        String newTitle = "Effective Java 2nd Edition";
        String newIsbn = "121234567890";

        savedBook.setTitle(newTitle);
        savedBook.setIsbn(newIsbn);
        Book updatedBook = bookRepository.save(savedBook);

        assertThat(updatedBook.getTitle()).isEqualTo(newTitle);
        assertThat(updatedBook.getIsbn()).isEqualTo(newIsbn);
    }

    @Test
    @Order(4)
    public void shouldFailUpdateBookWithInvalidData() {
        Book existingBook = bookRepository.save(book);

        existingBook.setTitle(null);
        existingBook.setIsbn(null);

        assertThrows(ConstraintViolationException.class, () -> {
            bookRepository.saveAndFlush(existingBook);
        });
    }

    @Test
    @Order(5)
    public void shouldListAllBooks() {
        bookRepository.save(book);
        List<Book> booksList = bookRepository.findAll();

        assertThat(booksList).isNotNull();
        assertThat(booksList.size()).isGreaterThan(0);
    }

    @Test
    @Order(6)
    public void shouldDeleteBookById() {
        Book bookToDelete = bookRepository.save(book);
        int bookId = bookToDelete.getId();

        bookRepository.deleteById(bookId);

        Optional<Book> deletedBook = bookRepository.findById(bookId);
        assertThat(deletedBook).isEmpty();
    }

    @Test
    @Order(7)
    public void shouldNotDeleteNonExistentBook() {
        int invalidBookId = 9999;

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookRepository.deleteById(invalidBookId);
        });
    }
}