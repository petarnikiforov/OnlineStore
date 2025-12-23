package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.online.store.dto.CartDetailsResponse;
import org.online.store.dto.CartDto;
import org.online.store.dto.CartResponse;
import org.online.store.models.Cart;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CartMapper {

    Cart dtoToModel(CartDto cartDto);
    CartResponse modelToResponse(Cart cart);
    CartDetailsResponse modelToDetailsResponse(Cart cart);

}
