package com.projectz.controller;

import com.projectz.common.dto.AksesCreateUpdateDto;
import com.projectz.common.dto.AksesDto;
import com.projectz.service.AksesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/akses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AksesController {

    private final AksesService aksesService;

    @GetMapping
    public Page<AksesDto> getAllAkses(Pageable pageable) {
        return aksesService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AksesDto> getAksesById(@PathVariable Long id) {
        return ResponseEntity.ok(aksesService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AksesDto> createAkses(@Valid @RequestBody AksesCreateUpdateDto dto) {
        AksesDto createdAkses = aksesService.create(dto);
        return new ResponseEntity<>(createdAkses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AksesDto> updateAkses(@PathVariable Long id, @Valid @RequestBody AksesCreateUpdateDto dto) {
        return ResponseEntity.ok(aksesService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAkses(@PathVariable Long id) {
        aksesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
