package ru.ifellow.ebredichina.userserice.enums;


public enum UserRole {
    CUSTOMER,
    STOREKEEPER,
    BOOKSELLER;

    public String getAuthority() {
        return name();
    }
}
