package com.gusparro.toauth.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PatchMapping("/{id}")
    public ResponseEntity<AppUser> partialUpdate(@PathVariable Long id, @RequestBody AppUser receivedAppUser) {
        return appUserService.findById(id).map(appUser -> {
            BeanUtils.copyProperties(receivedAppUser, appUser, getNullPropertyNames(receivedAppUser));

            appUserService.save(appUser);
            return ResponseEntity.ok(appUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return appUserService.findById(id).map(appUser -> {
                appUserService.deleteById(id);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(CONFLICT).build();
        }
    }

    private String[] getNullPropertyNames(AppUser receivedAppUser) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(receivedAppUser);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object propertyValue = beanWrapper.getPropertyValue(propertyDescriptor.getName());

            if (propertyValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }

        return emptyNames.toArray(new String[0]);
    }

}
