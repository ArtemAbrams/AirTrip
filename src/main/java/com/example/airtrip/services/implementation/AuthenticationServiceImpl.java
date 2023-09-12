package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.dataforrestapi.LoginData;
import com.example.airtrip.domain.data.dataforrestapi.RegistrationData;
import com.example.airtrip.domain.dto.dtoforrestapi.TokenResponse;
import com.example.airtrip.domain.entity.entityforrestspi.User;
import com.example.airtrip.domain.enums.AuthProvider;
import com.example.airtrip.repository.RoleRepository;
import com.example.airtrip.repository.UserRepository;
import com.example.airtrip.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceIml jwtServiceIml;

    @Override
    public String registration(RegistrationData registrationData) {
        var role = roleRepository.findRoleByRole("User")
                .orElseThrow(() -> new RuntimeException("Role was not found"));
        var user = User
                .builder()
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .email(registrationData.getEmail())
                .password(passwordEncoder.encode(registrationData.getPassword()))
                .roles(List.of(role))
                .name(registrationData.getName())
                .lastname(registrationData.getLastname())
                .authProvider(AuthProvider.local)
                .build();
        userRepository.saveAndFlush(user);

        return "You have registered";
    }

    @Override
    public TokenResponse login(LoginData loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          loginData.getEmail(),
          loginData.getPassword()
         )
        );
        var user = userRepository.findUserByEmail(loginData.getEmail())
                .orElseThrow(() -> new RuntimeException("User was not found"));
        return TokenResponse
                .builder()
                .token(jwtServiceIml.generateToken(user))
                .build();
    }
}
