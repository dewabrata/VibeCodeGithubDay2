package com.projectz.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserUpdateDto(
    @NotNull Long aksesId,
    @NotBlank @Size(max = 70) String namaLengkap,
    @Email @NotBlank @Size(max = 256) String email,
    @NotBlank @Size(max = 18) String noHp,
    @NotBlank @Size(max = 255) String alamat,
    @NotNull LocalDate tanggalLahir
) {}
