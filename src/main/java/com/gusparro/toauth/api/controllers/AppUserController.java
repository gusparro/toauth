package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.api.dtos.appuser.AppUserForm;
import com.gusparro.toauth.api.dtos.appuser.AppUserResponse;
import com.gusparro.toauth.api.dtos.appuser.AppUserSearchFilter;
import com.gusparro.toauth.api.dtos.role.RoleResponse;
import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.repositories.specifications.AppUserSpecifications;
import com.gusparro.toauth.domain.services.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    /* GET METHODS */

    @GetMapping
    public Page<AppUserResponse> getAll(AppUserSearchFilter searchFilter, Pageable pageable) {
        Page<AppUser> page = appUserService.findAll(AppUserSpecifications.searchFilter(searchFilter), pageable);
        List<AppUserResponse> appUserResponses = page.getContent().stream().map(AppUserResponse::fromAppUser).toList();

        return new PageImpl<>(appUserResponses, pageable, page.getTotalElements());
    }

    @GetMapping("/{codeUUID}")
    public AppUserResponse getByCode(@PathVariable String codeUUID) {
        return AppUserResponse.fromAppUser(appUserService.findByCodeUUID(codeUUID));
    }

    @GetMapping("/{codeUUID}/roles")
    public List<RoleResponse> getRolesPerAppUser(@PathVariable String codeUUID) {
        return AppUserResponse.fromAppUser(appUserService.findByCodeUUID(codeUUID)).roles();
    }

    /* POST METHODS */

    @PostMapping
    public ResponseEntity<AppUserResponse> persist(@RequestBody @Valid AppUserForm appUserForm) {
        AppUser persistedAppUser = appUserService.save(appUserForm.toAppUser());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codeUUID}")
                .buildAndExpand(persistedAppUser.getCodeUUID())
                .toUri();

        return ResponseEntity.created(uri).body(AppUserResponse.fromAppUser(persistedAppUser));
    }

    @PostMapping("/{codeUUIDAppUser}/roles/{idRole}")
    @ResponseStatus(NO_CONTENT)
    public void addRoleToAppUser(@PathVariable String codeUUIDAppUser, @PathVariable Long idRole) {
        appUserService.addRoleToAppUser(codeUUIDAppUser, idRole);
    }

    /* PUT METHODS */

    @PutMapping("/{codeUUID}")
    public AppUserResponse update(@PathVariable String codeUUID, @RequestBody AppUserForm appUserForm) {
        AppUser appUser = appUserService.findByCodeUUID(codeUUID);
        BeanUtils.copyProperties(appUserForm.toAppUser(), appUser, "id");

        return AppUserResponse.fromAppUser(appUserService.save(appUser));
    }

    /* PATCH METHODS */

    @PatchMapping("/{codeUUID}")
    public AppUserResponse partialUpdate(@PathVariable String codeUUID, @RequestBody AppUserForm appUserForm) {
        AppUser appUser = appUserService.findByCodeUUID(codeUUID);
        BeanUtils.copyProperties(appUserForm.toAppUser(), appUser, getNullPropertyNames(appUserForm.toAppUser()));

        return AppUserResponse.fromAppUser(appUserService.save(appUser));
    }

    /* DELETE METHODS */

    @DeleteMapping("/{codeUUID}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String codeUUID) {
        appUserService.deleteByCodeUUID(codeUUID);
    }

    @DeleteMapping("/{codeUUIDAppUser}/roles/{idRole}")
    @ResponseStatus(NO_CONTENT)
    public void removeRoleFromAppUser(@PathVariable String codeUUIDAppUser, @PathVariable Long idRole) {
        appUserService.removeRoleFromAppUser(codeUUIDAppUser, idRole);
    }

    /* PRIVATE METHODS */

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
