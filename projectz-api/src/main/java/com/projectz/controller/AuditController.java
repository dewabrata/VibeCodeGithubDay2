package com.projectz.controller;

import com.projectz.common.dto.ApiResponse;
import com.projectz.entity.LogKategoriProduk;
import com.projectz.entity.LogProduk;
import com.projectz.entity.LogSupplier;
import com.projectz.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Audit", description = "Audit Log Management API")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/products")
    @Operation(summary = "Get product audit logs", description = "Retrieve audit logs for products with optional filtering")
    public ResponseEntity<ApiResponse<Page<LogProduk>>> getProductAudits(
            @Parameter(description = "Filter by product ID") 
            @RequestParam(required = false) Long productId,
            @Parameter(description = "Filter by flag (I=Insert, U=Update, D=Delete)")
            @RequestParam(required = false) String flag,
            @Parameter(description = "Filter from date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @Parameter(description = "Filter to date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Pageable pageable) {
        
        Page<LogProduk> audits = auditService.getProductAudits(productId, flag, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(ApiResponse.success(audits, "Product audit logs retrieved successfully"));
    }

    @GetMapping("/categories")
    @Operation(summary = "Get category audit logs", description = "Retrieve audit logs for categories with optional filtering")
    public ResponseEntity<ApiResponse<Page<LogKategoriProduk>>> getCategoryAudits(
            @Parameter(description = "Filter by category ID")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Filter by flag (I=Insert, U=Update, D=Delete)")
            @RequestParam(required = false) String flag,
            @Parameter(description = "Filter from date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @Parameter(description = "Filter to date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Pageable pageable) {
        
        Page<LogKategoriProduk> audits = auditService.getCategoryAudits(categoryId, flag, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(ApiResponse.success(audits, "Category audit logs retrieved successfully"));
    }

    @GetMapping("/suppliers")
    @Operation(summary = "Get supplier audit logs", description = "Retrieve audit logs for suppliers with optional filtering")
    public ResponseEntity<ApiResponse<Page<LogSupplier>>> getSupplierAudits(
            @Parameter(description = "Filter by supplier ID")
            @RequestParam(required = false) Long supplierId,
            @Parameter(description = "Filter by flag (I=Insert, U=Update, D=Delete)")
            @RequestParam(required = false) String flag,
            @Parameter(description = "Filter from date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @Parameter(description = "Filter to date (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Pageable pageable) {
        
        Page<LogSupplier> audits = auditService.getSupplierAudits(supplierId, flag, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(ApiResponse.success(audits, "Supplier audit logs retrieved successfully"));
    }
}
