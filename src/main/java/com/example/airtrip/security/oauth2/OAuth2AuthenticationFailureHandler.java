package com.example.airtrip.security.oauth2;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Component
@Slf4j
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final static String redirectUrl = "http://localhost:3000/oauth2/redirect";
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        var url = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build()
                .toUriString();

        log.error(exception.getLocalizedMessage());
        getRedirectStrategy().sendRedirect(request, response, url);
    }
}
