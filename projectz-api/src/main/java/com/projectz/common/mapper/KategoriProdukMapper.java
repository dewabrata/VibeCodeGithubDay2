package com.projectz.common.mapper;

import com.projectz.common.dto.KategoriProdukCreateUpdateDto;
import com.projectz.common.dto.KategoriProdukDto;
import com.projectz.entity.MstKategoriProduk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface KategoriProdukMapper {
    KategoriProdukDto toDto(MstKategoriProduk kategoriProduk);
    MstKategoriProduk toEntity(KategoriProdukCreateUpdateDto dto);
    void updateEntityFromDto(KategoriProdukCreateUpdateDto dto, @MappingTarget MstKategoriProduk kategoriProduk);
}
