package com.amr.project.converter;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AddressMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "address", target = "address")
    UserDto userToDto(User user);

    @Mapping(source = "address", target = "address")
    User dtoToUser(UserDto userDto);

}
