package com.beulah.bookmanagementservice.exceptions;

public class BookDeletionException extends Throwable {
    public BookDeletionException(String s, Exception e) {
        super(s, e);
    }
}
