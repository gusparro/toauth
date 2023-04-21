package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.api.dtos.role.RoleForm;
import com.gusparro.toauth.api.dtos.role.RoleResponse;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.findAll().stream().map(RoleResponse::fromRole).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable Long id) {
        return RoleResponse.fromRole(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> persist(@RequestBody @Valid RoleForm roleForm) {
        Role persistedRole = roleService.save(roleForm.toRole());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(persistedRole.getId())
                .toUri();

        return ResponseEntity.created(uri).body(RoleResponse.fromRole(persistedRole));
    }

    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Long id, @RequestBody RoleForm roleForm) {
        Role role = roleService.findById(id);
        BeanUtils.copyProperties(roleForm.toRole(), role, "id");

        return RoleResponse.fromRole(roleService.save(role));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }

}
