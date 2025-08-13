package com.projectz.common.mapper;

import com.projectz.common.dto.SupplierCreateUpdateDto;
import com.projectz.common.dto.SupplierDto;
import com.projectz.entity.MstSupplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDto toDto(MstSupplier supplier);
    MstSupplier toEntity(SupplierCreateUpdateDto dto);
    void updateEntityFromDto(SupplierCreateUpdateDto dto, @MappingTarget MstSupplier supplier);
}
