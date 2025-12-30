package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.online.store.dto.*;
import org.online.store.models.Userr;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {CartMapper.class})
@Component
public interface UserMapper {
    UserDto requestToDto(UserRequest userRequest);
    Userr dtoToModel(UserDto userDto);
    UserResponse modelToResponse(Userr user);
    @Mapping(target = "cartId", source = "cart.cartId")
    UserDetailsResponse modelToDetailsResponse(Userr user);
}
