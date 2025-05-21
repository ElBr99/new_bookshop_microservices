package ru.ifellow.ebredichina.bookservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifellow.ebredichina.bookservice.dto.BookInfoDto;
import ru.ifellow.ebredichina.bookservice.dto.BookStorageDto;
import ru.ifellow.ebredichina.bookservice.dto.BuyBookDto;
import ru.ifellow.ebredichina.bookservice.dto.CreateBookInfoDto;
import ru.ifellow.ebredichina.bookservice.exception.BookStorageNotFoundException;
import ru.ifellow.ebredichina.bookservice.mapper.BookStorageMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoDtoMapper;
import ru.ifellow.ebredichina.bookservice.mapper.ToBookInfoMapper;
import ru.ifellow.ebredichina.bookservice.model.BookInfo;
import ru.ifellow.ebredichina.bookservice.model.BookStorage;
import ru.ifellow.ebredichina.bookservice.projections.BookStorageProjection;
import ru.ifellow.ebredichina.bookservice.repository.BookStorageRepository;
import ru.ifellow.ebredichina.bookservice.service.BookStorageService;
import ru.ifellow.ebredichina.bookservice.service.StorageService;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookStorageServiceImpl implements BookStorageService, StorageService<BookStorageDto> {

    private final CommonStorageService<BookStorage> bookStorageCommonStorageService;
    private final BookStorageRepository bookStorageRepository;
    private final ToBookInfoMapper toBookInfoMapper;
    private final ToBookInfoDtoMapper toBookInfoDtoMapper;
    private final BookStorageMapper bookStorageMapper;


    @Override
    public List<BookInfoDto> viewCurrentAssortment(UUID bookStorageId) {
        List<BookInfo> foundList = bookStorageCommonStorageService.viewCurrentAssortment(bookStorageId);
        return toBookInfoDtoMapper.listBookInfoToBookInfoDto(foundList);
    }


    @Override
    public void addBooksByPacks(UUID storageId, List<CreateBookInfoDto> createBookInfoDtoList) {
        bookStorageCommonStorageService.addBooksByPacks(
                storageId,
                toBookInfoMapper.mapToList(createBookInfoDtoList)
        );
    }

    @Override
    public BookStorageDto getStorageById(UUID storageId) {
        BookStorage foundBookStorage = bookStorageCommonStorageService.getStorageById(storageId);
        return bookStorageMapper.toBookStorageDto(foundBookStorage);
    }

    @Override
    public boolean isEnoughBooks(UUID bookId, int amount, UUID bookStorageId) {
        return bookStorageCommonStorageService.isEnoughBooks(bookId, amount, bookStorageId);
    }

    @Override
    public List<UUID> showAllBookStorageIds() {
        return bookStorageCommonStorageService.showAllBookStorageIds();
    }

    @Override
    public int getBookAmount(UUID bookShopId, UUID bookId) {
        return bookStorageCommonStorageService.getBookAmount(bookShopId, bookId);
    }


    @Override
    public BookStorage selectsStorageForDelivery(List<BuyBookDto> customerList) {
        Map<UUID, Integer> customerMap = customerList
                .stream()
                .collect(Collectors.toMap(BuyBookDto::getBookId, BuyBookDto::getAmount));

        List<UUID> bookInfoIds = customerList.stream()
                .map(BuyBookDto::getBookId)
                .toList();

        return bookStorageRepository.findAllStoragesWithBooks(bookInfoIds)
                .stream()
                .collect(Collectors.groupingBy(BookStorageProjection::getStorageId))
                .entrySet()
                .stream()
                .filter(uuidListEntry -> uuidListEntry.getValue().stream()
                        .allMatch(bookStorageProjection -> customerMap.containsKey(UUID.fromString(bookStorageProjection.getBookId()))
                                && customerMap.get(UUID.fromString(bookStorageProjection.getBookId())) <= bookStorageProjection.getAmount())
                )
                .findFirst()
                .map(Map.Entry::getKey)
                .map(id -> bookStorageRepository.findById(UUID.fromString(id)))
                .map(Optional::get)
                .orElseThrow(() -> new BookStorageNotFoundException("Склад для доставки не найден"));

    }

}
