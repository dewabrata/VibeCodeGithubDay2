package com.projectz.common.dto;

import java.time.LocalDateTime;

public record ProdukDto(
    Long id,
    String namaProduk,
    String merk,
    String model,
    String warna,
    String deskripsiProduk,
    Integer stok,
    String kategoriProduk, // Category name
    Long createdBy,
    LocalDateTime createdAt,
    Long modifiedBy,
    LocalDateTime modifiedAt
) {}
