package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.exceptions.role.RoleInUseException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import com.gusparro.toauth.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private static final String ROLE_NOT_FOUND_MESSAGE = "Role with id equal to %d does not exist.";
    private static final String ROLE_IN_USE_MESSAGE = "Role with id equal to %d cannot be removed, as it is in use.";

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_MESSAGE, id)));
    }

    public Role save(Role appUser) {
        return roleRepository.save(appUser);
    }

    public void deleteById(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND_MESSAGE, id));
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new RoleInUseException(String.format(ROLE_IN_USE_MESSAGE, id));
        }
    }

}
