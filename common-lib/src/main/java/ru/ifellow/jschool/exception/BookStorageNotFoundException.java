package ru.ifellow.jschool.exception;

public class BookStorageNotFoundException extends RuntimeException {

    public BookStorageNotFoundException(String message) {
        super(message);
    }

}
