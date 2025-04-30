package com.mybank.authservice_my_bank.web.controller;

import com.mybank.authservice_my_bank.domain.model.JwtAuthentication;
import com.mybank.authservice_my_bank.domain.service.AuthenticationService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final AuthenticationService authenticationService;

//    @PreAuthorize("hasAuthority('USER')")
//    @GetMapping("/user")
//    public ResponseEntity<Map<String, String>> helloUser() {
//        final JwtAuthentication authInfo = authenticationService.getAuthInfo();
//        Map<String, String> response = new HashMap<>();
//        response.put("name", authInfo.getName());
//        response.put("email", authInfo.getEmail());
//        response.put("role", String.valueOf(authInfo.getRole()));
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> helloUser() {
        final JwtAuthentication authInfo = authenticationService.getAuthInfo();
        Map<String, String> response = new HashMap<>();
        response.put("name", authInfo.getName());
        response.put("email", authInfo.getEmail());
        response.put("role", String.valueOf(authInfo.getRole()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authenticationService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body("Hello admin " + authInfo.getName() + "!");
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/owner")
    public ResponseEntity<String> helloOwner() {
        final JwtAuthentication authInfo = authenticationService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body("Hello admin " + authInfo.getName() + "!");
    }
}
