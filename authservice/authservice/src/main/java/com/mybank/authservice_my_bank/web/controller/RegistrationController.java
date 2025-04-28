package com.mybank.authservice_my_bank.web.controller;

import com.mybank.authservice_my_bank.web.mapper.WebDomainUserMapper;
import com.mybank.authservice_my_bank.web.model.RegistrationRequest;
import com.mybank.authservice_my_bank.web.model.RegistrationResponse;
import com.mybank.authservice_my_bank.domain.service.RegistrationService;
import com.mybank.authservice_my_bank.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final WebDomainUserMapper webDomainUserMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User user = webDomainUserMapper.toUserFromRegister(registrationRequest);
        User registeredUser = registrationService.register(user);
        RegistrationResponse registrationResponse = webDomainUserMapper.fromUser(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }
}
