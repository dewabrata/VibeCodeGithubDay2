package com.projectz.service;

import com.projectz.common.dto.SupplierCreateUpdateDto;
import com.projectz.common.dto.SupplierDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.SupplierMapper;
import com.projectz.entity.LogSupplier;
import com.projectz.entity.MstSupplier;
import com.projectz.repository.LogSupplierRepository;
import com.projectz.repository.MstSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final MstSupplierRepository supplierRepository;
    private final LogSupplierRepository logSupplierRepository;
    private final SupplierMapper supplierMapper;

    @Transactional(readOnly = true)
    public Page<SupplierDto> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable).map(supplierMapper::toDto);
    }

    @Transactional(readOnly = true)
    public SupplierDto findById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));
    }

    @Transactional
    public SupplierDto create(SupplierCreateUpdateDto dto) {
        MstSupplier supplier = supplierMapper.toEntity(dto);
        
        // TODO: Set createdBy from security context
        supplier.setCreatedBy(1L); // Placeholder
        supplier.setCreatedAt(LocalDateTime.now());

        MstSupplier savedSupplier = supplierRepository.save(supplier);

        // Create audit log
        createAuditLog(savedSupplier, "C");

        return supplierMapper.toDto(savedSupplier);
    }

    @Transactional
    public SupplierDto update(Long id, SupplierCreateUpdateDto dto) {
        MstSupplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));

        supplierMapper.updateEntityFromDto(dto, supplier);
        
        // TODO: Set modifiedBy from security context
        supplier.setModifiedBy(1L); // Placeholder
        supplier.setModifiedAt(LocalDateTime.now());

        MstSupplier updatedSupplier = supplierRepository.save(supplier);

        // Create audit log
        createAuditLog(updatedSupplier, "U");

        return supplierMapper.toDto(updatedSupplier);
    }

    @Transactional
    public void delete(Long id) {
        MstSupplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + id + " not found"));

        // Create audit log before deletion
        createAuditLog(supplier, "D");

        supplierRepository.deleteById(id);
    }

    private void createAuditLog(MstSupplier supplier, String flag) {
        LogSupplier log = new LogSupplier();
        log.setIdSupplier(supplier.getId());
        log.setNamaSupplier(supplier.getNamaSupplier());
        log.setAlamatSupplier(supplier.getAlamatSupplier());
        log.setFlag(flag);
        log.setCreatedAt(LocalDateTime.now());
        // TODO: Set createdBy from security context
        log.setCreatedBy(1L); // Placeholder

        logSupplierRepository.save(log);
    }
}
