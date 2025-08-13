package com.projectz.common.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDto(
    Long id,
    String username,
    String namaLengkap,
    String email,
    String noHp,
    String alamat,
    LocalDate tanggalLahir,
    String akses, // Role name
    Long createdBy,
    LocalDateTime createdDate,
    Long modifiedBy,
    LocalDateTime modifiedDate
) {}
