package com.projectz.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdukCreateUpdateDto(
    @NotNull Long idKategoriProduk,
    @NotBlank @Size(max = 50) String namaProduk,
    @NotBlank @Size(max = 50) String merk,
    @NotBlank @Size(max = 50) String model,
    @NotBlank @Size(max = 30) String warna,
    @NotBlank @Size(max = 255) String deskripsiProduk,
    @NotNull @Min(0) Integer stok
) {}
