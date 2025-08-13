package com.projectz.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierCreateUpdateDto(
    @NotBlank @Size(max = 50) String namaSupplier,
    @NotBlank @Size(max = 255) String alamatSupplier
) {}
