package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

}
