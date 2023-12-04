package org.example.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 토큰 생성 응답
@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;
}
