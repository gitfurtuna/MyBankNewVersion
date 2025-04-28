package com.mybank.authservice_my_bank.web.controller;

import com.mybank.authservice_my_bank.web.mapper.WebDomainUserMapper;
import com.mybank.authservice_my_bank.web.model.AuthJWTRequest;
import com.mybank.authservice_my_bank.web.model.AuthJWTResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.mybank.authservice_my_bank.domain.service.AuthenticationService;
import com.mybank.authservice_my_bank.domain.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;


@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final WebDomainUserMapper webDomainUserMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthJWTResponse> loginUser(@Valid @RequestBody AuthJWTRequest authJWTRequest, HttpServletResponse response) {
        User user = webDomainUserMapper.toUserFromAuth(authJWTRequest);
        final AuthJWTResponse authJWTResponse = authenticationService.login(user);

        Cookie accessCookie = new Cookie("access_token",authJWTResponse.getAccessToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 5);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", authJWTResponse.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 24 * 5);
        response.addCookie(refreshCookie);

        return ResponseEntity.status(HttpStatus.OK).body(authJWTResponse);

    }

        @PostMapping("/token")
        public ResponseEntity<AuthJWTResponse> getNewAccessToken(HttpServletRequest request) {
            Cookie[] cookies = request.getCookies();
            String refreshToken = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refresh_token".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }
            if (refreshToken != null) {
                final AuthJWTResponse authJWTResponse = authenticationService.getAccessToken(refreshToken);
                return ResponseEntity.status(HttpStatus.OK).body(authJWTResponse);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    @PostMapping("/refresh")
    public ResponseEntity<AuthJWTResponse> getNewRefreshToken(HttpServletRequest request) throws AuthenticationException {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken != null) {
        final AuthJWTResponse authJWTResponse = authenticationService.refresh(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(authJWTResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<AuthJWTResponse> oauth2Callback(@PathVariable String registrationId, @AuthenticationPrincipal OAuth2AuthenticationToken authentication, HttpServletResponse response) {
        OAuth2User principal = authentication.getPrincipal();
        String email = null;
        String name = null;
        if (principal != null) {
            email = authentication.getPrincipal().getAttribute("email");
            name = authentication.getPrincipal().getAttribute("name");
            if (email == null || email.isEmpty()) {
                String id = authentication.getPrincipal().getAttribute("id");
                email = id + registrationId + "@oauth2.com";
            if (name == null || name.isEmpty()) {
                name = authentication.getPrincipal().getAttribute("login");
                }
            }
        }

        final AuthJWTResponse authJWTResponse = authenticationService.loginOauth2(name,email);
//
//        JwtAuthentication jwtAuthentication = new JwtAuthentication();
//        jwtAuthentication.setEmail(email);
//        jwtAuthentication.setName(name);
//        jwtAuthentication.setAuthenticated(true);
//        jwtAuthentication.setRole(Role.USER);
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(jwtAuthentication);
//        SecurityContextHolder.setContext(context);
//
        Cookie accessCookie = new Cookie("access_token",authJWTResponse.getAccessToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 5);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", authJWTResponse.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 24 * 5);
        response.addCookie(refreshCookie);

        return ResponseEntity.status(HttpStatus.OK).body(authJWTResponse);

    }

}

