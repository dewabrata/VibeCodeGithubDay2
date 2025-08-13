package com.projectz.common.dto;

public record AuthResponseDto(String accessToken, String refreshToken, UserInfo userInfo) {
}
