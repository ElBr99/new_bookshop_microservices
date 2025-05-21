package ru.ifellow.ebredichina.userserice.exception;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
