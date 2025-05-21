package ru.ifellow.jschool.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public class SoldBook implements Serializable {

    @JsonProperty("id")
    private UUID bookId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("amount")
    private int amount;

    public SoldBook(UUID bookId, String title, int amount) {
        this.bookId = bookId;
        this.title = title;
        this.amount = amount;
    }

    public SoldBook() {
    }

    public UUID getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public int getAmount() {
        return amount;
    }
}
