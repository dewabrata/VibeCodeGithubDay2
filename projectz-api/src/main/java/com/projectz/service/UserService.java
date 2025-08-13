package com.projectz.service;

import com.projectz.common.dto.UserCreateDto;
import com.projectz.common.dto.UserDto;
import com.projectz.common.dto.UserUpdateDto;
import com.projectz.common.exception.ResourceNotFoundException;
import com.projectz.common.mapper.UserMapper;
import com.projectz.entity.MstAkses;
import com.projectz.entity.MstUser;
import com.projectz.repository.MstAksesRepository;
import com.projectz.repository.MstUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MstUserRepository userRepository;
    private final MstAksesRepository aksesRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    public UserDto create(UserCreateDto dto) {
        MstUser user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));

        MstAkses akses = aksesRepository.findById(dto.aksesId())
                .orElseThrow(() -> new ResourceNotFoundException("Akses with id " + dto.aksesId() + " not found"));
        user.setAkses(akses);

        // TODO: Set createdBy from security context
        user.setCreatedBy(1L); // Placeholder
        user.setCreatedDate(LocalDateTime.now());

        MstUser savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto update(Long id, UserUpdateDto dto) {
        MstUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        userMapper.updateEntityFromDto(dto, user);

        MstAkses akses = aksesRepository.findById(dto.aksesId())
                .orElseThrow(() -> new ResourceNotFoundException("Akses with id " + dto.aksesId() + " not found"));
        user.setAkses(akses);

        // TODO: Set modifiedBy from security context
        user.setModifiedBy(1L); // Placeholder
        user.setModifiedDate(LocalDateTime.now());

        MstUser updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
