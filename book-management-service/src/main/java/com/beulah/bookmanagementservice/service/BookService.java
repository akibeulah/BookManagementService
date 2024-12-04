package com.beulah.bookmanagementservice.service;

import com.beulah.bookmanagementservice.dto.NewBookRequest;
import com.beulah.bookmanagementservice.exceptions.BookAlreadyExistsException;
import com.beulah.bookmanagementservice.exceptions.BookDeletionException;
import com.beulah.bookmanagementservice.exceptions.InvalidBookTypeException;
import com.beulah.bookmanagementservice.exceptions.InvalidDateFormatException;
import com.beulah.bookmanagementservice.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface BookService {
    Optional<Book> createNewBook(NewBookRequest newBookRequest) throws InvalidBookTypeException, InvalidDateFormatException, BookAlreadyExistsException;
    Optional<Book> updateBook(NewBookRequest newBookRequest) throws InvalidDateFormatException, BookAlreadyExistsException, InvalidBookTypeException;
    Page<Book> listAllBooks(Pageable pageable, String query);
    void deleteBook(int id) throws BookDeletionException;
    Optional<Book> findById(int id);
}
