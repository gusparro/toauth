package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

}
