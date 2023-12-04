package org.example.springbootdeveloper.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.config.jwt.TokenProvider;
import org.example.springbootdeveloper.entity.RefreshToken;
import org.example.springbootdeveloper.entity.User;
import org.example.springbootdeveloper.repository.RefreshTokenRepository;
import org.example.springbootdeveloper.service.UserService;
import org.example.springbootdeveloper.util.CoockieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

//
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";
    
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository auth2AuthorizationRequestRepository;
    private final UserService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));
        
        // 토큰 제공자를 사용해 리프레시 토큰 생성 -> DB에 유저 아이디와 함께 저장 -> 클라이언트에서 액세스 토큰이 만료되면 재발급을 요청하도록
        // addRefreshTokenToCookie() 메서드를 호출하여 쿠키에 리프레시 토큰 저장
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        // 토큰 제공자를 사용해 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        // 쿠키에서 리다이렉트 경로가 담긴 값을 가져와 쿼리 파라미터에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        // 인증 관련 설정값, 쿠키 제거
        // 인증 프로세스를 진행하면서 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터를 제거
        // 기본으로 제공하는 clearAuthenticationAttributes()는 그대로 호출하고
        // removeAuthorizationRequestCookie()를 추가로 호출해 OAuth 인증을 위해 저장된 정보도 삭제
        clearAuthenticationAttributes(request, response);

        // 위에서 만든 URL로 redirect
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    // 생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CoockieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CoockieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    // 인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        auth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);   // OAuth 인증을 위해 젖아된 정보도 삭제
    }

    // 액세스 토큰을 패스에 추가
    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}
