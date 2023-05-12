package com.gusparro.toauth.api.dtos.appuser;

import com.gusparro.toauth.api.dtos.role.RoleForAppUser;
import com.gusparro.toauth.domain.entities.AppUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserForm {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 24)
    private String password;

    private List<RoleForAppUser> roles;

    public AppUser toAppUser() {
        return AppUser.builder()
                .fullName(fullName)
                .email(email)
                .username(username)
                .password(password)
                .roles(roles != null ? roles.stream().map(RoleForAppUser::toRole).collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

}
