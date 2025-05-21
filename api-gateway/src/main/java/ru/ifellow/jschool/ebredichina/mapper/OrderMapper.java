package ru.ifellow.jschool.ebredichina.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ifellow.jschool.ebredichina.dto.OrderDto;
import ru.ifellow.jschool.ebredichina.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customerEmail", target = "customerEmail")
    List<OrderDto> mapToListOrderDto(List<Order> order);

    @Mapping(source = "customerEmail", target = "customerEmail")
    OrderDto mapToOrderDto(Order order);

}
