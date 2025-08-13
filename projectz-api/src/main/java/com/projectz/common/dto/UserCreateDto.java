package com.projectz.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserCreateDto(
  @NotBlank @Size(min=4, max=16) String username,
  @NotBlank @Size(min=8) String password,
  @Email String email,
  @NotNull Long aksesId,
  @NotBlank String namaLengkap,
  String noHp,
  String alamat,
  LocalDate tanggalLahir
) {}
