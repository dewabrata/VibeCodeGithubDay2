package com.projectz.service;

import com.projectz.common.dto.KategoriProdukCreateUpdateDto;
import com.projectz.common.dto.KategoriProdukDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.KategoriProdukMapper;
import com.projectz.entity.LogKategoriProduk;
import com.projectz.entity.MstKategoriProduk;
import com.projectz.repository.LogKategoriProdukRepository;
import com.projectz.repository.MstKategoriProdukRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KategoriProdukService {

    private final MstKategoriProdukRepository kategoriProdukRepository;
    private final LogKategoriProdukRepository logKategoriProdukRepository;
    private final KategoriProdukMapper kategoriProdukMapper;

    @Transactional(readOnly = true)
    public Page<KategoriProdukDto> findAll(Pageable pageable) {
        return kategoriProdukRepository.findAll(pageable).map(kategoriProdukMapper::toDto);
    }

    @Transactional(readOnly = true)
    public KategoriProdukDto findById(Long id) {
        return kategoriProdukRepository.findById(id)
                .map(kategoriProdukMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk with id " + id + " not found"));
    }

    @Transactional
    public KategoriProdukDto create(KategoriProdukCreateUpdateDto dto) {
        MstKategoriProduk kategoriProduk = kategoriProdukMapper.toEntity(dto);
        
        // TODO: Set createdBy from security context
        kategoriProduk.setCreatedBy(1L); // Placeholder
        kategoriProduk.setCreatedAt(LocalDateTime.now());

        MstKategoriProduk savedKategoriProduk = kategoriProdukRepository.save(kategoriProduk);

        // Create audit log
        createAuditLog(savedKategoriProduk, "C");

        return kategoriProdukMapper.toDto(savedKategoriProduk);
    }

    @Transactional
    public KategoriProdukDto update(Long id, KategoriProdukCreateUpdateDto dto) {
        MstKategoriProduk kategoriProduk = kategoriProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk with id " + id + " not found"));

        kategoriProdukMapper.updateEntityFromDto(dto, kategoriProduk);
        
        // TODO: Set modifiedBy from security context
        kategoriProduk.setModifiedBy(1L); // Placeholder
        kategoriProduk.setModifiedAt(LocalDateTime.now());

        MstKategoriProduk updatedKategoriProduk = kategoriProdukRepository.save(kategoriProduk);

        // Create audit log
        createAuditLog(updatedKategoriProduk, "U");

        return kategoriProdukMapper.toDto(updatedKategoriProduk);
    }

    @Transactional
    public void delete(Long id) {
        MstKategoriProduk kategoriProduk = kategoriProdukRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk with id " + id + " not found"));

        // Create audit log before deletion
        createAuditLog(kategoriProduk, "D");

        kategoriProdukRepository.deleteById(id);
    }

    private void createAuditLog(MstKategoriProduk kategoriProduk, String flag) {
        LogKategoriProduk log = new LogKategoriProduk();
        log.setIdKategoriProduk(kategoriProduk.getId());
        log.setNamaProduk(kategoriProduk.getNamaProduk());
        log.setDeskripsi(kategoriProduk.getDeskripsi());
        log.setNotes(kategoriProduk.getNotes());
        log.setFlag(flag);
        log.setCreatedAt(LocalDateTime.now());
        // TODO: Set createdBy from security context
        log.setCreatedBy(1L); // Placeholder

        logKategoriProdukRepository.save(log);
    }
}
