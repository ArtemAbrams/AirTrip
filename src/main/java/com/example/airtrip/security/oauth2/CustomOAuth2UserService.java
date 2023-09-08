package com.example.airtrip.security.oauth2;

import com.example.airtrip.domain.entity.User;
import com.example.airtrip.domain.enums.AuthProvider;
import com.example.airtrip.domain.mapper.UserMapper;
import com.example.airtrip.exception.OAuth2AuthenticationProcessingException;
import com.example.airtrip.repository.RoleRepository;
import com.example.airtrip.repository.UserRepository;
import com.example.airtrip.security.user.OAuth2UserInfo;
import com.example.airtrip.security.user.OAuth2UserInfoFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return getOauth2(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            try {
                throw ex;
            } catch (OAuth2AuthenticationProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception ex) {
           throw new RuntimeException(ex.getMessage());
        }
    }
    private OAuth2User getOauth2(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationProcessingException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        var user = userRepository.findUserByEmail(oAuth2UserInfo.getEmail())
                .orElse(null);
        if(user != null) {
            if(!user.getAuthProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getAuthProvider() + " account. Please use your " + user.getAuthProvider()+
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserMapper.create(user);
    }
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        var role = roleRepository.findRoleByRole("User")
                .orElseThrow(() -> new RuntimeException("Role was not found"));

        var user = User.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .accountNonExpired(true)
                        .enabled(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .credentialsNonExpired(true)
                        .authProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                        .roles(List.of(role))
                        .lastname(oAuth2UserInfo.getLastName())
                        .name(oAuth2UserInfo.getName())
                        .build();

        return userRepository.save(user);

    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setLastname(oAuth2UserInfo.getLastName());
        existingUser.setEnabled(oAuth2UserInfo.isEmailEnable());
        return userRepository.save(existingUser);
    }
}
