package ru.ifellow.jschool.ebredichina.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.jschool.ebredichina.dto.OnlineOrderDto;
import ru.ifellow.jschool.ebredichina.model.Order;

@Mapper(componentModel = "spring")
public interface OrderToOnlineOrderDtoMapper {

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "customerBox", target = "customerBox")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "sum", target = "sum")
    OnlineOrderDto mapToOnlineOrderDto(Order order);

}
