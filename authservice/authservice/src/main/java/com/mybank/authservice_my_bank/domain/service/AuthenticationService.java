package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.datasource.mapper.DataDomainUserMapper;
import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.exception.PasswordIsIncorrectException;
import com.mybank.authservice_my_bank.domain.model.JwtAuthentication;
import com.mybank.authservice_my_bank.domain.model.Role;
import com.mybank.authservice_my_bank.web.model.AuthJWTResponse;
import com.mybank.authservice_my_bank.domain.exception.NotFoundUserException;
import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.datasource.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public  class AuthenticationService {

    private final UserRepository userRepository;

    private final DataDomainUserMapper dataDomainUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtProviderService jwtProviderService;

    private final RestTemplate restTemplate;

    public AuthJWTResponse login(User user) {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundUserException("User with " + user.getEmail() + " is not found"));
        ;
        if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new PasswordIsIncorrectException("Password is incorrect");
        } else {
            User userExist = dataDomainUserMapper.toUser(userEntity);
            final String accessToken = jwtProviderService.generateAccessToken(userExist);
            final String refreshToken = jwtProviderService.generateRefreshToken(userExist);
            refreshStorage.put(userExist.getEmail(), refreshToken);
            return new AuthJWTResponse(accessToken, refreshToken);
        }
    }

    public AuthJWTResponse loginOauth2(String name, String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseGet(() -> createNewOauth2User(name, email));
        User userExist = dataDomainUserMapper.toUser(userEntity);
        final String accessToken = jwtProviderService.generateAccessToken(userExist);
        final String refreshToken = jwtProviderService.generateRefreshToken(userExist);
        refreshStorage.put(userExist.getEmail(), refreshToken);
        return new AuthJWTResponse(accessToken, refreshToken);
    }


    public UserEntity createNewOauth2User(String name, String email) {
        UserEntity newUser = UserEntity.builder()
                .email(email)
                .name(name)
                .surname("Oauth2")
                .password(passwordEncoder.encode("Oauth2"))
                .dateOfBirth(LocalDate.of(1000, 10, 10))
                .phoneNumber("0-000-000-000")
                .role(Role.USER.getAuthority())
                .accountAmount(Role.USER.getCreditLimit())
                .dateTimeOfCreated(LocalDateTime.now())
                .dateTimeOfLastEnter(LocalDateTime.now())
                .build();

        return userRepository.save(newUser);
    }


    public AuthJWTResponse getAccessToken(@Valid String refreshToken) {
        if (jwtProviderService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProviderService.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundUserException("User with " + email + " is not found"));
                User userExist = dataDomainUserMapper.toUser(user);
                final String accessToken = jwtProviderService.generateAccessToken(userExist);
                return new AuthJWTResponse(accessToken, null);
            }
        }
        return new AuthJWTResponse(null, null);
    }

    public AuthJWTResponse refresh(@Valid String refreshToken) throws AuthenticationException {
        if (jwtProviderService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProviderService.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundUserException("User with " + email + " is not found"));
                User userExist = dataDomainUserMapper.toUser(user);
                final String accessToken = jwtProviderService.generateAccessToken(userExist);
                final String newRefreshToken = jwtProviderService.generateRefreshToken(userExist);
                refreshStorage.put(userExist.getEmail(), newRefreshToken);
                return new AuthJWTResponse(accessToken, refreshToken);
            }
        }

        throw new AuthenticationException("JWT token is not valid");
    }

    public JwtAuthentication getAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauth2Authentication) {
            OAuth2User principal = oauth2Authentication.getPrincipal();
            String email = null;
            String name = null;
            if (principal != null) {
                email = oauth2Authentication.getPrincipal().getAttribute("email");
                name = oauth2Authentication.getPrincipal().getAttribute("name");
                if (email == null || email.isEmpty()) {
                    String id = String.valueOf(Objects.requireNonNull(oauth2Authentication.getPrincipal().getAttribute("id")));
                    email = id + "@oauth2.com";
                    if (name == null || name.isEmpty()) {
                        name = oauth2Authentication.getPrincipal().getAttribute("login");
                    }
                }
            }
            JwtAuthentication jwtAuthentication = new JwtAuthentication();
            jwtAuthentication.setName(name);
            jwtAuthentication.setEmail(email);

            return jwtAuthentication;
        } else {
            return (JwtAuthentication) authentication;
        }
    }
}


