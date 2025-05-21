package ru.ifellow.jschool.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CUSTOMER,
    STOREKEEPER,
    BOOKSELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
