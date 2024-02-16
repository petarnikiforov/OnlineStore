package org.online.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.online.store.dto.*;
import org.online.store.models.Userr;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    UserDto requestToDto(UserRequest userRequest);
    Userr dtoToModel(UserDto userDto);
    @Mapping(target = "id", source = "id")
    UserResponse modelToResponse(Userr user);
}
