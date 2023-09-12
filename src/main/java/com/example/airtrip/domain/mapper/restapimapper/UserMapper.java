package com.example.airtrip.domain.mapper.restapimapper;

import com.example.airtrip.domain.entity.entityforrestspi.User;
import com.example.airtrip.security.UserDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public UserDTO create(User user) {

        return new UserDTO(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
