package com.beulah.bookmanagementservice.unit.controller;

import com.beulah.bookmanagementservice.controller.BookController;
import com.beulah.bookmanagementservice.dto.GenericResponse;
import com.beulah.bookmanagementservice.dto.MetaData;
import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.exceptions.BookAlreadyExistsException;
import com.beulah.bookmanagementservice.exceptions.BookDeletionException;
import com.beulah.bookmanagementservice.exceptions.InvalidBookTypeException;
import com.beulah.bookmanagementservice.exceptions.InvalidDateFormatException;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerUnitTests {

    @Mock
    private BookService bookService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BookController bookController;

    private NewBookRequest validBookRequest;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        validBookRequest = new NewBookRequest();
        validBookRequest.setTitle("Effective Java");
        validBookRequest.setIsbn("9780134685991");
        validBookRequest.setBookType("HARD_COVER");
        validBookRequest.setPrice(5274);
        validBookRequest.setPublishDate("2023-01-01");
        validBookRequest.setAuthor("Joshua Bloch");

        sampleBook = Book.builder()
                .id(1)
                .title(validBookRequest.getTitle())
                .isbn(validBookRequest.getIsbn())
                .bookType(BookType.SOFT_COVER)
                .price(validBookRequest.getPrice())
                .publishDate(LocalDate.parse(validBookRequest.getPublishDate()))
                .author(validBookRequest.getAuthor())
                .build();
    }

    @Test
    void shouldCreateBookWithValidData() throws InvalidBookTypeException, InvalidDateFormatException, BookAlreadyExistsException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.createNewBook(any(NewBookRequest.class)))
                .thenReturn(Optional.of(sampleBook));

        ResponseEntity<GenericResponse<Book>> response = bookController.createBook(validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book created successfully", response.getBody().getMessage());
        assertEquals(sampleBook, response.getBody().getData());

        verify(bookService).createNewBook(validBookRequest);
    }

    @Test
    void shouldFailToCreateBookWithInvalidData() throws InvalidDateFormatException, InvalidBookTypeException, BookAlreadyExistsException {
        when(bindingResult.hasErrors()).thenReturn(true);
        ObjectError error = new ObjectError("field", "Validation error");
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(error));

        ResponseEntity<GenericResponse<Book>> response = bookController.createBook(validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Failed to Create Book", response.getBody().getMessage());
        assertEquals(1, response.getBody().getErrors().size());
        verify(bookService, never()).createNewBook(any());
    }

    @Test
    void shouldUpdateBookWithValidData() throws InvalidDateFormatException, InvalidBookTypeException, BookAlreadyExistsException {
        int bookId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.updateBook(any(NewBookRequest.class)))
                .thenReturn(Optional.of(sampleBook));

        ResponseEntity<GenericResponse<Book>> response = bookController.updateBook(bookId, validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book updated successfully", response.getBody().getMessage());
        assertEquals(sampleBook, response.getBody().getData());
        assertEquals(bookId, validBookRequest.getId());

        verify(bookService).updateBook(validBookRequest);
    }

    @Test
    void shouldUpdateBookWithInvalidData() throws InvalidDateFormatException, InvalidBookTypeException, BookAlreadyExistsException {
        int bookId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.updateBook(any(NewBookRequest.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<GenericResponse<Book>> response = bookController.updateBook(bookId, validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        verify(bookService).updateBook(validBookRequest);
    }

    @Test
    void shouldListAllBooks() {
        List<Book> bookList = Collections.singletonList(sampleBook);
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> mockPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookService.listAllBooks(any(PageRequest.class), eq(null))).thenReturn(mockPage);

        ResponseEntity<GenericResponse<List<Book>>> response = bookController.listBooks(null, 0, 20);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Books retrieved successfully", response.getBody().getMessage());

        List<Book> returnedPage = response.getBody().getData();
        MetaData returnedMetaData = response.getBody().getMetaData();
        assertEquals(1, returnedMetaData.getTotalElements());
        assertEquals(0, returnedMetaData.getPage());
        assertEquals(20, returnedMetaData.getPerPage());
        assertEquals(1, returnedMetaData.getTotalPages());
        assertEquals(sampleBook, returnedPage.get(0));

        verify(bookService).listAllBooks(pageRequest, null);
    }

    @Test
    void shouldListAllBooksWithQuery() {
        String query = "Effective";

        List<Book> bookList = Collections.singletonList(sampleBook);
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> mockPage = new PageImpl<>(bookList, pageRequest, bookList.size());

        when(bookService.listAllBooks(any(PageRequest.class), eq(query))).thenReturn(mockPage);

        ResponseEntity<GenericResponse<List<Book>>> response = bookController.listBooks(query, 0, 20);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Books retrieved successfully", response.getBody().getMessage());

        List<Book> returnedPage = response.getBody().getData();
        MetaData returnedMetaData = response.getBody().getMetaData();
        assertEquals(1, returnedMetaData.getTotalElements());
        assertEquals(0, returnedMetaData.getPage());
        assertEquals(20, returnedMetaData.getPerPage());
        assertEquals(1, returnedMetaData.getTotalPages());
        assertEquals(sampleBook, returnedPage.get(0));

        verify(bookService).listAllBooks(pageRequest, query);
    }

    @Test
    void shouldDeleteBook() throws BookDeletionException {
        int bookId = 1;
        doNothing().when(bookService).deleteBook(bookId);

        ResponseEntity<GenericResponse<Void>> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book deleted successfully", response.getBody().getMessage());

        verify(bookService).deleteBook(bookId);
    }

    @Test
    void shouldFailToDeleteBookIfNotFound() throws BookDeletionException {
        int bookId = 1;
        doThrow(new EntityNotFoundException("Book not found")).when(bookService).deleteBook(bookId);

        ResponseEntity<GenericResponse<Void>> response = bookController.deleteBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book Not Found", response.getBody().getMessage());

        verify(bookService).deleteBook(bookId);
    }

    @Test
    void shouldReturnBookFoundById() {
        int bookId = 1;
        when(bookService.findById(bookId)).thenReturn(Optional.of(sampleBook));

        ResponseEntity<GenericResponse<Book>> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book retrieved successfully", response.getBody().getMessage());
        assertEquals(sampleBook, response.getBody().getData());

        verify(bookService).findById(bookId);
    }

    @Test
    @DisplayName("Get Book By ID - Not Found")
    void shouldFailToReturnBookFoundById() {
        int bookId = 1;
        when(bookService.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<GenericResponse<Book>> response = bookController.getBookById(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book Not Found", response.getBody().getMessage());
        assertEquals(1, response.getBody().getErrors().size());

        verify(bookService).findById(bookId);
    }

    @Test
    void shouldThrowBookAlreadyExistsException() throws BookAlreadyExistsException, InvalidDateFormatException, InvalidBookTypeException {
        when(bookService.createNewBook(any(NewBookRequest.class)))
                .thenThrow(new BookAlreadyExistsException("Book with this ISBN already exists"));

        BookAlreadyExistsException exception = assertThrows(
                BookAlreadyExistsException.class,
                () -> bookService.createNewBook(validBookRequest)
        );

        assertEquals("Book with this ISBN already exists", exception.getMessage());
    }

    @Test
    void shouldHandleServiceLevelExceptionsInController() throws InvalidDateFormatException, InvalidBookTypeException, BookAlreadyExistsException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookService.createNewBook(any(NewBookRequest.class)))
                .thenThrow(new BookAlreadyExistsException("Duplicate book"));

        ResponseEntity<GenericResponse<Book>> response = bookController.createBook(validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Duplicate Book Found", response.getBody().getMessage());
    }

    @Test
    void shouldHandleUpdateWithNonExistentBook() throws InvalidDateFormatException, InvalidBookTypeException, BookAlreadyExistsException {
        int nonExistentBookId = 999;
        when(bookService.updateBook(any(NewBookRequest.class)))
                .thenThrow(new EntityNotFoundException("Book not found"));

        ResponseEntity<GenericResponse<Book>> response = bookController.updateBook(nonExistentBookId, validBookRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Book Not Found", response.getBody().getMessage());
    }

    @Test
    void shouldValidateBookRequestConstraints() {
        NewBookRequest invalidRequest = new NewBookRequest();

        when(bindingResult.hasErrors()).thenReturn(true);
        ObjectError error = new ObjectError("field", "Validation constraint violated");
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(error));

        ResponseEntity<GenericResponse<Book>> response = bookController.createBook(invalidRequest, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
    }
}