package com.zhuzirui.brt.mapper;

import com.zhuzirui.brt.model.dto.UserDTO;
import com.zhuzirui.brt.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStructMapper {
    UserStructMapper INSTANCE = Mappers.getMapper(UserStructMapper.class);

    UserDTO entityToDto(User user);

    User dtoToEntity(UserDTO dto);
}
