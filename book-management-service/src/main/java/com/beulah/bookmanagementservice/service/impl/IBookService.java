package com.beulah.bookmanagementservice.service.impl;

import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.enums.BookType;
import com.beulah.bookmanagementservice.exceptions.BookAlreadyExistsException;
import com.beulah.bookmanagementservice.exceptions.BookDeletionException;
import com.beulah.bookmanagementservice.exceptions.InvalidBookTypeException;
import com.beulah.bookmanagementservice.exceptions.InvalidDateFormatException;
import com.beulah.bookmanagementservice.models.Book;
import com.beulah.bookmanagementservice.repository.BookRepository;
import com.beulah.bookmanagementservice.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class IBookService implements BookService {
    private final BookRepository bookRepository;

    public IBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> createNewBook(NewBookRequest newBookRequest) throws InvalidBookTypeException, InvalidDateFormatException, BookAlreadyExistsException {
        try {
            checkBookUniqueness(newBookRequest.getIsbn());
            BookType bookType = validateBookType(newBookRequest.getBookType());
            LocalDate parsePublishDate = validatePublishDate(newBookRequest.getPublishDate());

            Book newBook = Book.builder()
                    .title(newBookRequest.getTitle())
                    .isbn(newBookRequest.getIsbn())
                    .price(newBookRequest.getPrice())
                    .bookType(bookType)
                    .author(newBookRequest.getAuthor())
                    .publishDate(parsePublishDate)
                    .build();
            newBook = bookRepository.save(newBook);
            return Optional.of(newBook);
        } catch (IllegalArgumentException e) {
            throw new InvalidBookTypeException("Invalid book type: " + newBookRequest.getBookType());
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid publication date format: " + newBookRequest.getPublishDate());
        }
    }

    @Override
    public Optional<Book> updateBook(NewBookRequest newBookRequest) throws InvalidDateFormatException, BookAlreadyExistsException, InvalidBookTypeException {
        Book book = bookRepository.findById(newBookRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Book with id %d not found", newBookRequest.getId())
                ));

        if (!book.getIsbn().equals(newBookRequest.getIsbn()))
            checkBookUniqueness(newBookRequest.getIsbn());

        updateBookFields(book, newBookRequest);
        return Optional.of(bookRepository.save(book));
    }

    private void updateBookFields(Book book, NewBookRequest request) throws InvalidDateFormatException, InvalidBookTypeException {
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(validatePublishDate(request.getPublishDate()));
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getBookType() != null) {
            book.setBookType(validateBookType(request.getBookType()));
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
    }

    @Override
    public Page<Book> listAllBooks(Pageable pageable, String query) {
        if (query == null || query.isEmpty()) {
            return bookRepository.findAll(pageable);
        }
        return bookRepository.searchAllBooks(query, pageable);
    }

    @Override
    public void deleteBook(int id) throws BookDeletionException {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Book with id %d not found", id));
        }

        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new BookDeletionException("Failed to delete book with id " + id, e);
        }
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    private BookType validateBookType(String bookTypeString) throws InvalidBookTypeException {
        try {
            return BookType.valueOf(bookTypeString);
        } catch (IllegalArgumentException e) {
            throw new InvalidBookTypeException("Invalid book type: " + bookTypeString);
        }
    }

    private LocalDate validatePublishDate(String publishDateString) throws InvalidDateFormatException {
        try {
            return LocalDate.parse(publishDateString);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid publication date format: " + publishDateString);
        }
    }

    private void checkBookUniqueness(String isbn) throws BookAlreadyExistsException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            throw new BookAlreadyExistsException(
                    String.format("Book with ISBN %s already exists", isbn)
            );
        }
    }

    private Book buildBookFromRequest(
            NewBookRequest request,
            BookType bookType,
            LocalDate publishDate
    ) {
        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .bookType(bookType)
                .author(request.getAuthor())
                .publishDate(publishDate)
                .build();
    }
}