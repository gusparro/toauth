package com.gusparro.toauth.api.dtos.appuser;

import com.gusparro.toauth.api.dtos.role.RoleResponse;
import com.gusparro.toauth.domain.entities.AppUser;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record AppUserResponse (Long id, String fullName, String email, String username, List<RoleResponse> roles) {
    public static AppUserResponse fromAppUser(AppUser appUser) {
        return AppUserResponse.builder()
                .id(appUser.getId())
                .fullName(appUser.getFullName())
                .email(appUser.getEmail())
                .username(appUser.getUsername())
                .roles(appUser.getRoles().stream().map(RoleResponse::fromRole).collect(Collectors.toList()))
                .build();
    }
}
