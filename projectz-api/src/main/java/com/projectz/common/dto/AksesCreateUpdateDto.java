package com.projectz.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AksesCreateUpdateDto(
    @NotBlank @Size(max = 50) String nama,
    @NotBlank @Size(max = 255) String deskripsi
) {}
