package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.online.store.dto.CartDto;
import org.online.store.dto.CartRequest;
import org.online.store.dto.CartResponse;
import org.online.store.models.Cart;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CartMapper {

    CartDto requestToDto(CartRequest cartRequest);
    Cart dtoToModel(CartDto cartDto);
    CartResponse modelToResponse(Cart cart);

}
