package ru.ifellow.jschool.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.jschool.dto.OnlineOrderDto;
import ru.ifellow.jschool.orderservice.model.Order;

@Mapper(componentModel = "spring")
public interface OrderToOnlineOrderDtoMapper {

    @Mapping(source = "customerBox", target = "customerBox")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "sum", target = "sum")
    OnlineOrderDto mapToOnlineOrderDto(Order order);

}
