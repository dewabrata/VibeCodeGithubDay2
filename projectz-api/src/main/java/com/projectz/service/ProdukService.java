package com.projectz.service;

import com.projectz.common.dto.ProdukCreateUpdateDto;
import com.projectz.common.dto.ProdukDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.ProdukMapper;
import com.projectz.entity.LogProduk;
import com.projectz.entity.MstKategoriProduk;
import com.projectz.entity.MstProduk;
import com.projectz.repository.LogProdukRepository;
import com.projectz.repository.MstKategoriProdukRepository;
import com.projectz.repository.MstProdukRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProdukService {

    private final MstProdukRepository produkRepository;
    private final MstKategoriProdukRepository kategoriProdukRepository;
    private final LogProdukRepository logProdukRepository;
    private final ProdukMapper produkMapper;

    @Transactional(readOnly = true)
    public Page<ProdukDto> findAll(Pageable pageable) {
        return produkRepository.findAll(pageable).map(produkMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProdukDto findById(Long id) {
        return produkRepository.findById(id)
                .map(produkMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Produk with id " + id + " not found"));
    }

    @Transactional
    public ProdukDto create(ProdukCreateUpdateDto dto) {
        MstProduk produk = produkMapper.toEntity(dto);

        MstKategoriProduk kategoriProduk = kategoriProdukRepository.findById(dto.idKategoriProduk())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk with id " + dto.idKategoriProduk() + " not found"));
        produk.setKategoriProduk(kategoriProduk);

        // TODO: Set createdBy from security context
        produk.setCreatedBy(1L); // Placeholder
        produk.setCreatedAt(LocalDateTime.now());

        MstProduk savedProduk = produkRepository.save(produk);

        // Create audit log
        createAuditLog(savedProduk, "C");

        return produkMapper.toDto(savedProduk);
    }

    @Transactional
    public ProdukDto update(Long id, ProdukCreateUpdateDto dto) {
        MstProduk produk = produkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk with id " + id + " not found"));

        produkMapper.updateEntityFromDto(dto, produk);

        MstKategoriProduk kategoriProduk = kategoriProdukRepository.findById(dto.idKategoriProduk())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori Produk with id " + dto.idKategoriProduk() + " not found"));
        produk.setKategoriProduk(kategoriProduk);

        // TODO: Set modifiedBy from security context
        produk.setModifiedBy(1L); // Placeholder
        produk.setModifiedAt(LocalDateTime.now());

        MstProduk updatedProduk = produkRepository.save(produk);

        // Create audit log
        createAuditLog(updatedProduk, "U");

        return produkMapper.toDto(updatedProduk);
    }

    @Transactional
    public void delete(Long id) {
        MstProduk produk = produkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk with id " + id + " not found"));

        // Create audit log before deletion
        createAuditLog(produk, "D");

        produkRepository.deleteById(id);
    }

    private void createAuditLog(MstProduk produk, String flag) {
        LogProduk log = new LogProduk();
        log.setIdProduk(produk.getId());
        log.setIdKategoriProduk(produk.getKategoriProduk().getId());
        log.setNamaProduk(produk.getNamaProduk());
        log.setMerk(produk.getMerk());
        log.setModel(produk.getModel());
        log.setWarna(produk.getWarna());
        log.setDeskripsiProduk(produk.getDeskripsiProduk());
        log.setStok(produk.getStok());
        log.setFlag(flag);
        log.setCreatedAt(LocalDateTime.now());
        // TODO: Set createdBy from security context
        log.setCreatedBy(1L); // Placeholder

        logProdukRepository.save(log);
    }
}
