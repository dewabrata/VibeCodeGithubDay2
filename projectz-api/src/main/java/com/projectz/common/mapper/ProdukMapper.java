package com.projectz.common.mapper;

import com.projectz.common.dto.ProdukCreateUpdateDto;
import com.projectz.common.dto.ProdukDto;
import com.projectz.entity.MstProduk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdukMapper {

    @Mapping(target = "kategoriProduk", source = "kategoriProduk.namaProduk")
    ProdukDto toDto(MstProduk produk);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kategoriProduk", ignore = true) // Will be set manually in service
    MstProduk toEntity(ProdukCreateUpdateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kategoriProduk", ignore = true)
    void updateEntityFromDto(ProdukCreateUpdateDto dto, @MappingTarget MstProduk produk);
}
