package com.eshop.model.user;

import com.eshop.dto.user.UserCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserEntity fromCreateRequestDto(UserCreateRequestDto requestDto);

}
