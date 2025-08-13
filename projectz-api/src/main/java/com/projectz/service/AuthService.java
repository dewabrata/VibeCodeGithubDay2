package com.projectz.service;

import com.projectz.common.dto.AuthRequestDto;
import com.projectz.common.dto.AuthResponseDto;
import com.projectz.common.dto.UserInfo;
import com.projectz.entity.MstUser;
import com.projectz.repository.MstUserRepository;
import com.projectz.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final MstUserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponseDto login(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        final MstUser user = userRepository.findByUsername(request.username()).get();

        final String accessToken = jwtUtil.generateToken(userDetails);
        // For simplicity, using the same token as refresh token. In real app, generate a separate one.
        final String refreshToken = jwtUtil.generateToken(userDetails); 

        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), user.getNamaLengkap(), user.getAkses().getNama());

        return new AuthResponseDto(accessToken, refreshToken, userInfo);
    }
}
