package com.projectz.common.mapper;

import com.projectz.common.dto.AksesCreateUpdateDto;
import com.projectz.common.dto.AksesDto;
import com.projectz.entity.MstAkses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AksesMapper {
    AksesDto toDto(MstAkses akses);
    MstAkses toEntity(AksesCreateUpdateDto dto);
    void updateEntityFromDto(AksesCreateUpdateDto dto, @MappingTarget MstAkses akses);
}
