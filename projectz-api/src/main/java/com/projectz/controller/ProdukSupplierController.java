package com.projectz.controller;

import com.projectz.common.dto.SupplierDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.SupplierMapper;
import com.projectz.entity.MapProdukSupplier;
import com.projectz.entity.MapProdukSupplierId;
import com.projectz.entity.MstProduk;
import com.projectz.entity.MstSupplier;
import com.projectz.repository.MapProdukSupplierRepository;
import com.projectz.repository.MstProdukRepository;
import com.projectz.repository.MstSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProdukSupplierController {

    private final MstProdukRepository produkRepository;
    private final MstSupplierRepository supplierRepository;
    private final MapProdukSupplierRepository mapProdukSupplierRepository;
    private final SupplierMapper supplierMapper;

    @GetMapping("/{productId}/suppliers")
    public List<SupplierDto> getProductSuppliers(@PathVariable Long productId) {
        if (!produkRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product with id " + productId + " not found");
        }

        return mapProdukSupplierRepository.findAll().stream()
                .filter(map -> map.getId().getIdProduk().equals(productId))
                .map(map -> supplierMapper.toDto(map.getSupplier()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{productId}/suppliers")
    public ResponseEntity<Void> updateProductSuppliers(
            @PathVariable Long productId,
            @RequestBody List<Long> supplierIds) {

        MstProduk produk = produkRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

        // Remove existing mappings
        List<MapProdukSupplier> existingMappings = mapProdukSupplierRepository.findAll().stream()
                .filter(map -> map.getId().getIdProduk().equals(productId))
                .collect(Collectors.toList());
        mapProdukSupplierRepository.deleteAll(existingMappings);

        // Add new mappings
        for (Long supplierId : supplierIds) {
            MstSupplier supplier = supplierRepository.findById(supplierId)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + supplierId + " not found"));

            MapProdukSupplierId mapId = new MapProdukSupplierId();
            mapId.setIdProduk(productId);
            mapId.setIdSupplier(supplierId);

            MapProdukSupplier mapping = new MapProdukSupplier();
            mapping.setId(mapId);
            mapping.setProduk(produk);
            mapping.setSupplier(supplier);

            mapProdukSupplierRepository.save(mapping);
        }

        return ResponseEntity.noContent().build();
    }
}
