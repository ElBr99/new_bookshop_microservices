package ru.ifellow.jschool.ebredichina.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.ifellow.jschool.ebredichina.dto.CreateChequeDto;
import ru.ifellow.jschool.ebredichina.model.Cheque;
import ru.ifellow.jschool.ebredichina.service.OrderService;
import ru.ifellow.jschool.ebredichina.service.impl.OfflineBookShopClientServiceImpl;

@Mapper(componentModel = "spring",
        uses = {OfflineBookShopMapper.class, OrderService.class, OfflineBookShopClientServiceImpl.class})
public abstract class ToChequeMapperOnline {

    @Lazy
    @Autowired
    protected OrderService orderService;

    @Autowired
    protected OfflineBookShopClientServiceImpl offlineBookShopService;

    @Autowired
    protected OfflineBookShopMapper offlineBookShopMapper;

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
