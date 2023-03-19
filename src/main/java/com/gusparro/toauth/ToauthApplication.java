package com.gusparro.toauth;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.repositories.RoleRepository;
import com.gusparro.toauth.domain.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToauthApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService appUserService, RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new Role(null, "DRIVER_ROLE",
                    "Profile created for use by company drivers.", null));

            roleRepository.save(new Role(null, "MANAGER_ROLE",
                    "Profile created for use by company managers.", null));

            roleRepository.save(new Role(null, "ADMINISTRATOR_ROLE",
                    "Profile created for use by company administrators.", null));

            appUserService.save(new AppUser(null, "Gustavo Ferreira Parro",
                    "gustavo.ferreira.parro@gmail.com", "gusparro", "123456", null));

            appUserService.addRoleToAppUser(1L, "DRIVER_ROLE");
            appUserService.addRoleToAppUser(1L, "MANAGER_ROLE");
            appUserService.addRoleToAppUser(1L, "ADMINISTRATOR_ROLE");

            appUserService.save(new AppUser(null, "Iann Carlo",
                    "iannCarlo@gmail.com", "iann", "123456", null));

            appUserService.addRoleToAppUser(2L, "MANAGER_ROLE");

            appUserService.save(new AppUser(null, "Wendel Silva",
                    "wendwl_silva@gmail.com", "wendelzin", "123456", null));

            appUserService.addRoleToAppUser(2L, "DRIVER_ROLE");
        };
    }

}
