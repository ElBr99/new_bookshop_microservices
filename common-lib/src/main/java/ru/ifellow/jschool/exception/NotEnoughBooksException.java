package ru.ifellow.jschool.exception;

public class NotEnoughBooksException extends RuntimeException {

    public NotEnoughBooksException(String message) {
        super(message);
    }
}
