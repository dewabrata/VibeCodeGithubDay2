package com.projectz.controller;

import com.projectz.common.dto.ProdukCreateUpdateDto;
import com.projectz.common.dto.ProdukDto;
import com.projectz.service.ProdukService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class ProdukController {

    private final ProdukService produkService;

    @GetMapping
    public Page<ProdukDto> getAllProducts(Pageable pageable) {
        return produkService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdukDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(produkService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdukDto> createProduct(@Valid @RequestBody ProdukCreateUpdateDto dto) {
        ProdukDto createdProduct = produkService.create(dto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdukDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProdukCreateUpdateDto dto) {
        return ResponseEntity.ok(produkService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        produkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
