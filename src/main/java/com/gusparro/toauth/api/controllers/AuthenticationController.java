package com.gusparro.toauth.api.controllers;

import com.gusparro.toauth.api.dtos.AuthenticationResponse;
import com.gusparro.toauth.api.dtos.SignInRequestForm;
import com.gusparro.toauth.api.dtos.SignUpRequestForm;
import com.gusparro.toauth.domain.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequestForm signUpRequestForm) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequestForm));
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInRequestForm signInRequestForm) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequestForm));
    }

}
