package com.projectz.common.dto;

import java.time.LocalDateTime;

public record KategoriProdukDto(
    Long id,
    String namaProduk,
    String deskripsi,
    String notes,
    Long createdBy,
    LocalDateTime createdAt,
    Long modifiedBy,
    LocalDateTime modifiedAt
) {}
