package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.entity.RefreshToken;
import org.example.springbootdeveloper.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

// 새로 만들어 전달받은 리프레시 토큰으로 리프레시 토큰 객체를 검색해서 전달하는 메소드 구현
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
