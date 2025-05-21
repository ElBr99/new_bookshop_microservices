package ru.ifellow.jschool.ebredichina.exception;

public class PaymentFailedException extends RuntimeException{

    public PaymentFailedException(String message) {
        super(message);
    }

}
