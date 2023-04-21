package com.gusparro.toauth.api.dtos.role;

import com.gusparro.toauth.domain.entities.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleForm {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public Role toRole() {
        return Role.builder()
                .name(name)
                .description(description)
                .build();
    }

}
