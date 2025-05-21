package ru.ifellow.jschool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmitterMessage {

    private String userId;
    private String message;
    private List<SendBookInfo> favouriteBooks;
}
