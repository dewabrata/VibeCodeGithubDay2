package com.projectz.common.mapper;

import com.projectz.common.dto.UserCreateDto;
import com.projectz.common.dto.UserDto;
import com.projectz.common.dto.UserUpdateDto;
import com.projectz.entity.MstUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "akses", source = "akses.nama")
    UserDto toDto(MstUser user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "akses", ignore = true) // Will be set manually in service
    @Mapping(target = "password", ignore = true) // Will be set manually in service
    MstUser toEntity(UserCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "akses", ignore = true)
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget MstUser user);
}
