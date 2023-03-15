package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAll() {
        return appUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getById(@PathVariable Long id) {
        return appUserService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AppUser persist(@RequestBody AppUser appUser) {
        return appUserService.save(appUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> update(@PathVariable Long id, @RequestBody AppUser receivedAppUser) {
        return appUserService.findById(id).map(appUser -> {
            BeanUtils.copyProperties(receivedAppUser, appUser, "id");
            appUserService.save(appUser);
            return ResponseEntity.ok(appUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            return appUserService.findById(id).map(appUser -> {
                appUserService.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(CONFLICT).build();
        }
    }

}
