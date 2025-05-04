package com.mybank.authservice_my_bank.web.controller;

import com.mybank.authservice_my_bank.domain.model.JwtAuthentication;
import com.mybank.authservice_my_bank.domain.model.Photo;
import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.domain.service.AuthenticationService;

import com.mybank.authservice_my_bank.domain.service.AuthorizationService;
import com.mybank.authservice_my_bank.web.mapper.WebDomainUserMapper;
import com.mybank.authservice_my_bank.web.model.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    private final AuthorizationService authorizationService;

    private final WebDomainUserMapper webDomainUserMapper;

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
    public ResponseEntity<Map<String, String>> helloPage() {
        final JwtAuthentication authInfo = authenticationService.getAuthInfo();
        Map<String, String> response = new HashMap<>();
        response.put("name", authInfo.getName());
        response.put("email", authInfo.getEmail());
        response.put("role", String.valueOf(authInfo.getRole()));
        response.put("surname", authInfo.getSurname());
        response.put("password", authInfo.getPassword());
        response.put("phoneNumber", authInfo.getPhoneNumber());
        response.put("dateOfBirth", String.valueOf(authInfo.getDateOfBirth()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("profile/{role}/{email}/update")
    public ResponseEntity<RegistrationRequest> updateProfile(@PathVariable String role ,
                                              @PathVariable String email,
                                              @RequestBody RegistrationRequest registrationRequest) {

        User user = webDomainUserMapper.toUserFromRegister(registrationRequest);
        User userUpdate = authorizationService.updateProfile(user);
        RegistrationRequest request = webDomainUserMapper.fromUserToRegistrationRequest(userUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }


    @PostMapping("profile/{role}/{email}/photo")
    public ResponseEntity<Photo> updatePhoto(@PathVariable String role ,
                                             @PathVariable String email ,
                                             @RequestParam("file") MultipartFile file ) throws IOException {
        User user = authorizationService.updatePhoto(email,file);
        Photo photo = user.getPhoto();

        return ResponseEntity.status(HttpStatus.CREATED).body(photo);
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
