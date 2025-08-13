package com.projectz.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KategoriProdukCreateUpdateDto(
    @NotBlank @Size(max = 50) String namaProduk,
    @NotBlank @Size(max = 255) String deskripsi,
    @NotBlank @Size(max = 255) String notes
) {}
