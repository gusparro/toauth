package com.gusparro.toauth.domain.services;

import com.gusparro.toauth.api.security.JwtService;
import com.gusparro.toauth.domain.dtos.AuthenticationResponse;
import com.gusparro.toauth.domain.dtos.SignInRequestForm;
import com.gusparro.toauth.domain.dtos.SignUpRequestForm;
import com.gusparro.toauth.domain.entities.AppUser;
import com.gusparro.toauth.domain.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequestForm form) {
        AppUser appUser = AppUser.builder()
                .fullName(form.getFullName())
                .email(form.getEmail())
                .username(form.getUsername())
                .password(encoder.encode(form.getPassword()))
                .build();

        String token = jwtService.generateToken(appUserRepository.save(appUser));

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse signIn(SignInRequestForm form) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        AppUser appUser = appUserRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no found."));

        String token = jwtService.generateToken(appUser);

        return new AuthenticationResponse(token);
    }
}
