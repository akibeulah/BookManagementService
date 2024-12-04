package com.beulah.bookmanagementservice.unit.service;

import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.exceptions.BookAlreadyExistsException;
import com.beulah.bookmanagementservice.exceptions.BookDeletionException;
import com.beulah.bookmanagementservice.exceptions.InvalidBookTypeException;
import com.beulah.bookmanagementservice.exceptions.InvalidDateFormatException;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.repository.BookRepository;
import com.beulah.bookmanagementservice.service.impl.IBookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceUnitTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private IBookService bookService;

    private Book book;
    private NewBookRequest newBookRequest;

    @BeforeEach
    public void setup() {
        book = Book.builder()
                .id(1)
                .title("Effective Java")
                .isbn("9780134685991")
                .price(5274)
                .bookType(BookType.SOFT_COVER)
                .author("Joshua Bloch")
                .publishDate(LocalDate.now())
                .build();

        newBookRequest = new NewBookRequest();
        newBookRequest.setId(1);
        newBookRequest.setTitle("Effective Java");
        newBookRequest.setIsbn("9780134685991");
        newBookRequest.setPrice(5274);
        newBookRequest.setBookType("SOFT_COVER");
        newBookRequest.setAuthor("Joshua Bloch");
        newBookRequest.setPublishDate(LocalDate.now().toString());
    }

    @Test
    @Order(1)
    public void shouldCreateNewBookWithValidData() throws InvalidBookTypeException, InvalidDateFormatException, BookAlreadyExistsException {
        given(bookRepository.save(any(Book.class))).willReturn(book);

        Optional<Book> savedBook = bookService.createNewBook(newBookRequest);

        assertThat(savedBook).isPresent();
        assertThat(savedBook.get().getTitle()).isEqualTo("Effective Java");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @Order(2)
    public void shouldThrowExceptionForInvalidBookType() {
        newBookRequest.setBookType("INVALID_TYPE");

        assertThrows(InvalidBookTypeException.class, () -> {
            bookService.createNewBook(newBookRequest);
        });
    }

    @Test
    @Order(3)
    public void shouldThrowExceptionForIncompleteBookData() {
        given(bookRepository.save(any(Book.class))).willThrow(ConstraintViolationException.class);

        NewBookRequest failingBookRequest = newBookRequest;
        failingBookRequest.setTitle(null);

        assertThrows(ConstraintViolationException.class, () -> {
            bookService.createNewBook(newBookRequest);
        });
    }

    @Test
    @Order(4)
    public void shouldThrowExceptionForInvalidDateFormat() {
        newBookRequest.setPublishDate("invalid-date");

        assertThrows(InvalidDateFormatException.class, () -> {
            bookService.createNewBook(newBookRequest);
        });
    }

    @Test
    @Order(5)
    public void shouldUpdateBookSuccessfully() throws InvalidDateFormatException, BookAlreadyExistsException, InvalidBookTypeException {
        given(bookRepository.findById(newBookRequest.getId())).willReturn(Optional.of(book));
        given(bookRepository.save(any(Book.class))).willReturn(book);

        NewBookRequest updateRequest = new NewBookRequest();
        updateRequest.setId(1);
        updateRequest.setTitle("Updated Title");
        updateRequest.setPrice(60);

        Optional<Book> updatedBook = bookService.updateBook(updateRequest);

        assertThat(updatedBook).isPresent();
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @Order(6)
    public void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentBook() {
        given(bookRepository.findById(newBookRequest.getId())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateBook(newBookRequest);
        });
    }

    @Test
    @Order(7)
    public void shouldListAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> mockPage = mock(Page.class);

        given(bookRepository.findAll(pageable)).willReturn(mockPage);

        Page<Book> resultPage = bookService.listAllBooks(pageable, null);

        assertThat(resultPage).isNotNull();
        verify(bookRepository).findAll(pageable);
    }

    @Test
    @Order(8)
    public void shouldDeleteBookSuccessfully() throws BookDeletionException {
        given(bookRepository.existsById(book.getId())).willReturn(true);
        doNothing().when(bookRepository).deleteById(book.getId());

        bookService.deleteBook(book.getId());
        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    @Order(9)
    public void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentBook() {
        given(bookRepository.existsById(9999)).willReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteBook(9999);
        });
    }

    @Test
    @Order(10)
    public void shouldThrowBookDeletionExceptionWhenDeletionFails() {
        given(bookRepository.existsById(book.getId())).willReturn(true);
        doThrow(new RuntimeException("Database error")).when(bookRepository).deleteById(book.getId());

        assertThrows(BookDeletionException.class, () -> {
            bookService.deleteBook(book.getId());
        });
    }
}