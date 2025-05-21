package ru.ifellow.ebredichina.bookservice.exception;

public class NotEnoughBooksException extends RuntimeException {

    public NotEnoughBooksException(String message) {
        super(message);
    }
}
