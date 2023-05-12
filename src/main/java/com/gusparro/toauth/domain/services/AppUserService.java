package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.entities.Role;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserDuplicateKeyException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserInUseException;
import com.gusparro.toauth.domain.exceptions.appuser.AppUserNotFoundException;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppUserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with code equal to %s does not exist.";
    private static final String USER_IN_USE_MESSAGE = "User with id equal to %s cannot be removed, as it is in use.";
    private static final String USER_DUPLICATE_EMAIL_MESSAGE = "This email is already in use.";
    private static final String USER_DUPLICATE_USERNAME_MESSAGE = "This username is already in use";

    private final AppUserRepository appUserRepository;

    private final RoleService roleService;

    private final PasswordEncoder encoder;

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findByCode(String code) {
        return appUserRepository.findByCode(code)
                .orElseThrow(() -> new AppUserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, code)));
    }

    public AppUser save(AppUser appUser) {
        try {
            if (appUser.getPassword() != null) {
                appUser.setPassword(encoder.encode(appUser.getPassword()));
            }

            if (appUser.getRoles() != null && !appUser.getRoles().isEmpty()) {
                appUser.getRoles().replaceAll(role -> roleService.findById(role.getId()));
            }

            return appUserRepository.save(appUser);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getMessage().contains("email")) {
                throw new AppUserDuplicateKeyException(USER_DUPLICATE_EMAIL_MESSAGE);
            }

            throw new AppUserDuplicateKeyException(USER_DUPLICATE_USERNAME_MESSAGE);
        }
    }

    public void deleteByCode(String code) {
        try {
            appUserRepository.deleteById(this.findByCode(code).getId());
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            throw new AppUserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, code));
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            throw new AppUserInUseException(String.format(USER_IN_USE_MESSAGE, code));
        }
    }

    @Transactional
    public void addRoleToAppUser(String appUserCode, Long roleId) {
        AppUser appUser = this.findByCode(appUserCode);
        Role role = roleService.findById(roleId);

        if (appUser.getRoles().contains(role)) {
            return;
        }

        appUser.getRoles().add(role);
    }

    @Transactional
    public void removeRoleFromAppUser(String appUserCode, Long roleId) {
        AppUser appUser = this.findByCode(appUserCode);
        Role role = roleService.findById(roleId);

        if (!appUser.getRoles().contains(role)) {
            return;
        }

        appUser.getRoles().remove(role);
    }

}
