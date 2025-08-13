package com.projectz.common.dto;

import java.time.LocalDateTime;

public record SupplierDto(
    Long id,
    String namaSupplier,
    String alamatSupplier,
    Long createdBy,
    LocalDateTime createdAt,
    Long modifiedBy,
    LocalDateTime modifiedAt
) {}
