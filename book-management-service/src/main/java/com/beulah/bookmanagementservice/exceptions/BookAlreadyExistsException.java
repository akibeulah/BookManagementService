package com.beulah.bookmanagementservice.exceptions;

public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String s) {
        super(s);
    }
}
