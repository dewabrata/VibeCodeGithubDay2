package com.projectz.controller;

import com.projectz.common.dto.AuthRequestDto;
import com.projectz.common.dto.AuthResponseDto;
import com.projectz.common.dto.UserInfo;
import com.projectz.entity.MstUser;
import com.projectz.repository.MstUserRepository;
import com.projectz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MstUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfo> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        MstUser user = userRepository.findByUsername(username).orElseThrow();
        UserInfo userInfo = new UserInfo(
            user.getId(), 
            user.getUsername(), 
            user.getNamaLengkap(), 
            user.getAkses() != null ? user.getAkses().getNama() : "DEFAULT_ROLE"
        );
        
        return ResponseEntity.ok(userInfo);
    }
}
