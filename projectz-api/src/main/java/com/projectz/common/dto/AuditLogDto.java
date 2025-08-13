package com.projectz.common.dto;

import java.time.LocalDateTime;

public record AuditLogDto(
    Long id,
    String tableName,
    Long recordId,
    String action,
    String data,
    Long createdBy,
    LocalDateTime createdAt
) {}
