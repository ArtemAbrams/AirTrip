package com.example.airtrip.configuration;


import com.example.airtrip.security.RestAuthenticationEntryPoint;
import com.example.airtrip.security.oauth2.CustomOAuth2UserService;
import com.example.airtrip.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.example.airtrip.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterConfiguration {
    private final SecurityFilter securityFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(e -> e.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(e ->
                        e.requestMatchers("/authorize/**",
                                        "/oauth2/**",
                                        "/conflict/country/**",
                                        "/product/**",
                                        "/order/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(e -> e.authorizationEndpoint(f -> f
                        .baseUri("/oauth2/authorize"))
                        .redirectionEndpoint(f -> f.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(f -> f.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()));
        return http.build();
    }

}
