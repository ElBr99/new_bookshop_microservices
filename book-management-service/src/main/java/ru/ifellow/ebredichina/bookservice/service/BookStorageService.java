package ru.ifellow.ebredichina.bookservice.service;

import ru.ifellow.ebredichina.bookservice.dto.BuyBookDto;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;

import java.util.List;

public interface BookStorageService {

    BookStorage selectsStorageForDelivery(List<BuyBookDto> customerList);

}
