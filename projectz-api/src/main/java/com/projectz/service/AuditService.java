package com.projectz.service;

import com.projectz.entity.LogKategoriProduk;
import com.projectz.entity.LogProduk;
import com.projectz.entity.LogSupplier;
import com.projectz.repository.LogKategoriProdukRepository;
import com.projectz.repository.LogProdukRepository;
import com.projectz.repository.LogSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final LogProdukRepository logProdukRepository;
    private final LogKategoriProdukRepository logKategoriProdukRepository;
    private final LogSupplierRepository logSupplierRepository;

    public Page<LogProduk> getProductAudits(Long productId, String flag, 
                                          LocalDateTime dateFrom, LocalDateTime dateTo, 
                                          Pageable pageable) {
        return logProdukRepository.findByFilters(productId, flag, dateFrom, dateTo, pageable);
    }

    public Page<LogKategoriProduk> getCategoryAudits(Long categoryId, String flag, 
                                                   LocalDateTime dateFrom, LocalDateTime dateTo, 
                                                   Pageable pageable) {
        return logKategoriProdukRepository.findByFilters(categoryId, flag, dateFrom, dateTo, pageable);
    }

    public Page<LogSupplier> getSupplierAudits(Long supplierId, String flag, 
                                             LocalDateTime dateFrom, LocalDateTime dateTo, 
                                             Pageable pageable) {
        return logSupplierRepository.findByFilters(supplierId, flag, dateFrom, dateTo, pageable);
    }
}