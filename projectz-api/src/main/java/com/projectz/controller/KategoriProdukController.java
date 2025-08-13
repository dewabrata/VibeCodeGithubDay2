package com.projectz.controller;

import com.projectz.common.dto.KategoriProdukCreateUpdateDto;
import com.projectz.common.dto.KategoriProdukDto;
import com.projectz.service.KategoriProdukService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class KategoriProdukController {

    private final KategoriProdukService kategoriProdukService;

    @GetMapping
    public Page<KategoriProdukDto> getAllCategories(Pageable pageable) {
        return kategoriProdukService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KategoriProdukDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(kategoriProdukService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KategoriProdukDto> createCategory(@Valid @RequestBody KategoriProdukCreateUpdateDto dto) {
        KategoriProdukDto createdCategory = kategoriProdukService.create(dto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KategoriProdukDto> updateCategory(@PathVariable Long id, @Valid @RequestBody KategoriProdukCreateUpdateDto dto) {
        return ResponseEntity.ok(kategoriProdukService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        kategoriProdukService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
