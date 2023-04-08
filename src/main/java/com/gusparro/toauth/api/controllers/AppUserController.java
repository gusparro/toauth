package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import com.gusparro.toauth.domain.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.beans.PropertyDescriptor;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<?> handleAppUserNotFoundException(AppUserNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(AppUserInUseException.class)
    public ResponseEntity<?> handleAppUserInUseException(AppUserInUseException exception) {
        return ResponseEntity.status(CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping
    public List<AppUser> getAll() {
        return appUserService.findAll();
    }

    @GetMapping("/{id}")
    public AppUser getById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AppUser> persist(@RequestBody AppUser appUser) {
        AppUser persistedAppUser = appUserService.save(appUser);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(persistedAppUser.getId())
                .toUri();

        return ResponseEntity.created(uri).body(persistedAppUser);
    }

    @PutMapping("/{id}")
    public AppUser update(@PathVariable Long id, @RequestBody AppUser receivedAppUser) {
        AppUser appUser = appUserService.findById(id);
        BeanUtils.copyProperties(receivedAppUser, appUser, "id");

        return appUserService.save(appUser);
    }

    @PatchMapping("/{id}")
    public AppUser partialUpdate(@PathVariable Long id, @RequestBody AppUser receivedAppUser) {
        AppUser appUser = appUserService.findById(id);
        BeanUtils.copyProperties(receivedAppUser, appUser, getNullPropertyNames(receivedAppUser));

        return appUserService.save(appUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        appUserService.deleteById(id);
    }

    @PostMapping("/{idAppUser}/add-role/{idRole}")
    public void addRoleToAppUser(@PathVariable Long idAppUser, @PathVariable Long idRole) {
        appUserService.addRoleToAppUser(idAppUser, idRole);
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
