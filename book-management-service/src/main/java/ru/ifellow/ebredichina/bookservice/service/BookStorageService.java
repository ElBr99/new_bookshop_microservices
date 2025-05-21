package ru.ifellow.ebredichina.bookservice.service;


import ru.ifellow.jschool.dto.BookStorageDto;
import ru.ifellow.jschool.dto.BuyBookDto;
import java.util.List;

public interface BookStorageService {

    BookStorageDto selectsStorageForDelivery(List<BuyBookDto> customerList);

}
