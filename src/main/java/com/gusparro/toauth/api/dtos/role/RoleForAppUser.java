package com.gusparro.toauth.api.dtos.role;

import com.gusparro.toauth.domain.entities.Role;

public record RoleForAppUser(Long id) {

    public Role toRole() {
        return Role.builder()
                .id(id)
                .build();
    }

}
