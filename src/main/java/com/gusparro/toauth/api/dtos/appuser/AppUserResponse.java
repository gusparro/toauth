package com.gusparro.toauth.api.dtos.appuser;

import com.gusparro.toauth.api.dtos.role.RoleResponse;
import com.gusparro.toauth.domain.entities.AppUser;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record AppUserResponse(String codeUUID, String fullName, String email, String username, List<RoleResponse> roles) {
    public static AppUserResponse fromAppUser(AppUser appUser) {
        return AppUserResponse.builder()
                .codeUUID(appUser.getCodeUUID())
                .fullName(appUser.getFullName())
                .email(appUser.getEmail())
                .username(appUser.getUsername())
                .roles(appUser.getRoles() != null ?
                        appUser.getRoles().stream().map(RoleResponse::fromRole).collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }
}
