package com.gusparro.toauth.domain.repositories;

import com.gusparro.toauth.domain.entities.AppUser;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AppUserRepositoryTest {

    @Autowired
    AppUserRepository userRepository;

    @Test
    public void itShouldRegistrateUserSuccessfullyWhenAllAttributesAreValid() {
        AppUser appUser = AppUser.builder()
                .fullName("Daniel Silva")
                .email("dasilva@gmail.com")
                .username("dasilva")
                .password("123456")
                .build();

        appUser = userRepository.save(appUser);

        assertThat(appUser.getId()).isNotNull();
    }

    @Test
    public void itShouldThrowConstraintViolationExceptionWhenEmailIsInvalid() {
        AppUser appUser = AppUser.builder()
                .fullName("Mayra Ferreira Netto")
                .email("mayra#gmail.com")
                .username("mayra")
                .password("123456")
                .build();

        assertThrows(ConstraintViolationException.class, () -> userRepository.save(appUser));
    }

    @Test
    public void itShouldThrowConstraintViolationExceptionWhenFieldIsNull() {
        AppUser appUser = AppUser.builder()
                .fullName("Mayra Ferreira Netto")
                .email("mayra@gmail.com")
                .password("123456")
                .build();

        assertThrows(ConstraintViolationException.class, () -> userRepository.save(appUser));
    }

    @Test
    public void itShouldThrowConstraintViolationExceptionWhenFieldIsEmpty() {
        AppUser appUser = AppUser.builder()
                .fullName("")
                .email("mayra@gmail.com")
                .username("mayra")
                .password("123456")
                .build();

        assertThrows(ConstraintViolationException.class, () -> userRepository.save(appUser));
    }

    @Test
    public void itShouldThrowConstraintViolationExceptionWhenFieldIsBlank() {
        AppUser appUser = AppUser.builder()
                .fullName("Mayra Ferreira Netto")
                .email("mayra@gmail.com")
                .username("mayra")
                .password("    ")
                .build();

        assertThrows(ConstraintViolationException.class, () -> userRepository.save(appUser));
    }

}
