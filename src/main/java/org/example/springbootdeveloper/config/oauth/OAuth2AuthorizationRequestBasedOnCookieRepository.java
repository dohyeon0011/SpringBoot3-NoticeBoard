package org.example.springbootdeveloper.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springbootdeveloper.util.CoockieUtil;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

// OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓸 수 있도록 인증 요청과 관련된 상태를 저장할 저장소
// OAuth의 정보를 가져오고 저장하는 로직
public class OAuth2AuthorizationRequestBasedOnCookieRepository implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CoockieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }

        CoockieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CoockieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CoockieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
