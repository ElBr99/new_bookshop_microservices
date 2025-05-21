package ru.ifellow.jschool.exception;

public class ChequeNotFoundException extends RuntimeException {

    public ChequeNotFoundException(String message) {
        super(message);
    }
}
