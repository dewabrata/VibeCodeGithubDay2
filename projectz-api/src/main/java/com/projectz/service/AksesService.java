package com.projectz.service;

import com.projectz.common.dto.AksesCreateUpdateDto;
import com.projectz.common.dto.AksesDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.AksesMapper;
import com.projectz.entity.MstAkses;
import com.projectz.repository.MstAksesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AksesService {

    private final MstAksesRepository aksesRepository;
    private final AksesMapper aksesMapper;

    @Transactional(readOnly = true)
    public Page<AksesDto> findAll(Pageable pageable) {
        return aksesRepository.findAll(pageable).map(aksesMapper::toDto);
    }

    @Transactional(readOnly = true)
    public AksesDto findById(Long id) {
        return aksesRepository.findById(id)
                .map(aksesMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Akses with id " + id + " not found"));
    }

    @Transactional
    public AksesDto create(AksesCreateUpdateDto dto) {
        MstAkses akses = aksesMapper.toEntity(dto);
        // TODO: Set createdBy from security context
        akses.setCreatedBy(1L); // Placeholder
        akses.setCreatedDate(LocalDateTime.now());
        MstAkses savedAkses = aksesRepository.save(akses);
        return aksesMapper.toDto(savedAkses);
    }

    @Transactional
    public AksesDto update(Long id, AksesCreateUpdateDto dto) {
        MstAkses akses = aksesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Akses with id " + id + " not found"));

        aksesMapper.updateEntityFromDto(dto, akses);
        // TODO: Set modifiedBy from security context
        akses.setModifiedBy(1L); // Placeholder
        akses.setModifiedDate(LocalDateTime.now());

        MstAkses updatedAkses = aksesRepository.save(akses);
        return aksesMapper.toDto(updatedAkses);
    }

    @Transactional
    public void delete(Long id) {
        if (!aksesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Akses with id " + id + " not found");
        }
        aksesRepository.deleteById(id);
    }
}
