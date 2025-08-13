package com.projectz.common.dto;

import java.time.LocalDateTime;

public record AksesDto(
    Long id,
    String nama,
    String deskripsi,
    Long createdBy,
    LocalDateTime createdDate,
    Long modifiedBy,
    LocalDateTime modifiedDate
) {}
