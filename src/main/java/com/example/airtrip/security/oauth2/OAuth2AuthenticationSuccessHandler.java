package com.example.airtrip.security.oauth2;


import com.example.airtrip.exception.BadRequestUrl;
import com.example.airtrip.security.UserDTO;
import com.example.airtrip.services.implementation.JwtServiceIml;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtServiceIml tokenProvider;
    private final static String redirectUrl = "http://localhost:3000/oauth2/redirect";
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        var userPrincipal = (UserDTO) authentication.getPrincipal();
        String token = tokenProvider.generateToken(userPrincipal);


        return UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("token", token)
                .build().toUriString();
    }
}
