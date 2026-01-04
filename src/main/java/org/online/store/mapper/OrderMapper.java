package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.online.store.dto.*;
import org.online.store.models.OrderItem;
import org.online.store.models.Orderr;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderMapper {
    OrderDto requestToDto(OrderRequest orderRequest);
    Orderr dtoToModel(OrderDto orderDto);
    OrderResponse modelToResponse(Orderr order);
    OrderDetailsResponse modelToDetailsResponse(Orderr order);
    OrderItemResponse itemToResponse(OrderItem item);
}
