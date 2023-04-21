package com.gusparro.toauth.api.dtos.role;

import com.gusparro.toauth.domain.entities.Role;
import lombok.Builder;

@Builder
public record RoleResponse(Long id, String name) {

    public static RoleResponse fromRole(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

}
