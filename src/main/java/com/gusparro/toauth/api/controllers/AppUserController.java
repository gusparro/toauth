package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.api.dtos.appuser.AppUserForm;
import com.gusparro.toauth.api.dtos.appuser.AppUserResponse;
import com.gusparro.toauth.api.dtos.role.RoleResponse;
import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.services.AppUserService;
import jakarta.validation.Valid;
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
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public List<AppUserResponse> getAll() {
        return appUserService.findAll().stream().map(AppUserResponse::fromAppUser).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppUserResponse getById(@PathVariable Long id) {
        return AppUserResponse.fromAppUser(appUserService.findById(id));
    }

    @GetMapping("/{id}/roles")
    public List<RoleResponse> getRolesPerAppUser(@PathVariable Long id) {
        return AppUserResponse.fromAppUser(appUserService.findById(id)).roles();
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> persist(@RequestBody @Valid AppUserForm appUserForm) {
        AppUser persistedAppUser = appUserService.save(appUserForm.toAppUser());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(persistedAppUser.getId())
                .toUri();

        return ResponseEntity.created(uri).body(AppUserResponse.fromAppUser(persistedAppUser));
    }

    @PutMapping("/{id}")
    public AppUserResponse update(@PathVariable Long id, @RequestBody AppUserForm appUserForm) {
        AppUser appUser = appUserService.findById(id);
        BeanUtils.copyProperties(appUserForm.toAppUser(), appUser, "id");

        return AppUserResponse.fromAppUser(appUserService.save(appUser));
    }

    @PatchMapping("/{id}")
    public AppUserResponse partialUpdate(@PathVariable Long id, @RequestBody AppUserForm appUserForm) {
        AppUser appUser = appUserService.findById(id);
        BeanUtils.copyProperties(appUserForm.toAppUser(), appUser, getNullPropertyNames(appUserForm.toAppUser()));

        return AppUserResponse.fromAppUser(appUserService.save(appUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        appUserService.deleteById(id);
    }

    @PostMapping("/{idAppUser}/roles/add/{idRole}")
    @ResponseStatus(NO_CONTENT)
    public void addRoleToAppUser(@PathVariable Long idAppUser, @PathVariable Long idRole) {
        appUserService.addRoleToAppUser(idAppUser, idRole);
    }

    @PostMapping("/{idAppUser}/roles/remove/{idRole}")
    @ResponseStatus(NO_CONTENT)
    public void removeRoleFromAppUser(@PathVariable Long idAppUser, @PathVariable Long idRole) {
        appUserService.removeRoleFromAppUser(idAppUser, idRole);
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
