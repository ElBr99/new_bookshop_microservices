package ru.ifellow.jschool.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.jschool.dto.CreateChequeDto;
import ru.ifellow.jschool.orderservice.model.Cheque;

@Mapper(componentModel = "spring")
public abstract class ToChequeMapperOnline {

    @Mapping(target = "offlineShopPurchase", expression = "java(offlineBookShopMapper.toOfflineBookShop(offlineBookShopService.getStorageById(createChequeDto.getPurchasePlaceId())))")
    @Mapping(target = "order", expression = "java(orderService.getOrder(createChequeDto.getOrderId()))")
    @Mapping(target = "purchaseDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ssXXX")
    public abstract Cheque mapToCheque(CreateChequeDto createChequeDto);

    @Mapping(target = "orderId", expression = "java(cheque.getOrder().getId())")
    @Mapping(target = "customerEmail", source = "cheque.customerEmail")
    @Mapping(target = "customerBox", source = "cheque.order.customerBox")
    @Mapping(target = "sum", source = "cheque.sum")
    @Mapping(target = "purchaseDate", expression = "java(cheque.getPurchaseDate().truncatedTo(java.time.temporal.ChronoUnit.SECONDS))")
    @Mapping(target = "chequeType", source = "cheque.chequeType")
    public abstract CreateChequeDto mapToCreateChequeDto(Cheque cheque);

}
