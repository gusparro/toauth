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

}
