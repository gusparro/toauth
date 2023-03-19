package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import com.gusparro.toauth.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final RoleRepository roleRepository;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public void deleteById(Long id) {
        appUserRepository.deleteById(id);
    }

    @Transactional
    public void addRoleToAppUser(Long appUserId, String roleName) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new RuntimeException("AppUser not found: " + appUserId));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        if (appUser.getRoles().contains(role)) {
            return;
        }

        appUser.getRoles().add(role);
    }

}
