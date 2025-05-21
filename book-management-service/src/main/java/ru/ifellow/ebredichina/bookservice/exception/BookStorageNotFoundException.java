package ru.ifellow.ebredichina.bookservice.exception;

public class BookStorageNotFoundException extends RuntimeException {

    public BookStorageNotFoundException(String message) {
        super(message);
    }

}
