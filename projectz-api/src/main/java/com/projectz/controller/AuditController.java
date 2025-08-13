package com.projectz.controller;

import com.projectz.entity.LogKategoriProduk;
import com.projectz.entity.LogProduk;
import com.projectz.entity.LogSupplier;
import com.projectz.repository.LogKategoriProdukRepository;
import com.projectz.repository.LogProdukRepository;
import com.projectz.repository.LogSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final LogProdukRepository logProdukRepository;
    private final LogKategoriProdukRepository logKategoriProdukRepository;
    private final LogSupplierRepository logSupplierRepository;

    @GetMapping("/products")
    public Page<LogProduk> getProductAudits(
            @RequestParam(required = false) Long productId,
            Pageable pageable) {
        return logProdukRepository.findAll(pageable);
    }

    @GetMapping("/categories")
    public Page<LogKategoriProduk> getCategoryAudits(
            @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        return logKategoriProdukRepository.findAll(pageable);
    }

    @GetMapping("/suppliers")
    public Page<LogSupplier> getSupplierAudits(
            @RequestParam(required = false) Long supplierId,
            Pageable pageable) {
        return logSupplierRepository.findAll(pageable);
    }
}
