package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.exceptions.role.RoleNotFoundException;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppUserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id equal to %d does not exist.";
    private static final String USER_IN_USE_MESSAGE = "User with id equal to %d cannot be removed, as it is in use.";

    private final AppUserRepository appUserRepository;

    private final RoleService roleService;

    private final PasswordEncoder encoder;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    public AppUser save(AppUser appUser) {
        if (appUser.getPassword() != null) {
            appUser.setPassword(encoder.encode(appUser.getPassword()));
        }

        return appUserRepository.save(appUser);
    }

    public void deleteById(Long id) {
        try {
            appUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            throw new AppUserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id));
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new AppUserInUseException(String.format(USER_IN_USE_MESSAGE, id));
        }
    }

    @Transactional
    public void addRoleToAppUser(Long appUserId, Long roleId) {
        AppUser appUser = this.findById(appUserId);
        Role role = roleService.findById(roleId);

        if (appUser.getRoles().contains(role)) {
            return;
        }

        appUser.getRoles().add(role);
    }

}
