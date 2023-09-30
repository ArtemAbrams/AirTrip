package com.example.airtrip.configuration;

import com.example.airtrip.domain.entity.entityforrestspi.Role;
import com.example.airtrip.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleConfiguration implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final static String ADMIN_ROLE = "ADMIN";
    private final static String USER_ROLE = "ADMIN";
    @Override
    public void run(String... args) throws Exception {
        var admin = roleRepository.findRoleByRole(ADMIN_ROLE)
                .isPresent();
        var user = roleRepository.findRoleByRole(USER_ROLE)
                .isPresent();
        checkRole(admin, ADMIN_ROLE);
        checkRole(user, USER_ROLE);
    }
    private void checkRole(boolean role, String nameOfRole){
        if(!role){
            var role_entity = Role.builder()
                    .role(nameOfRole)
                    .build();
            roleRepository.saveAndFlush(role_entity);
        }
    }
}
