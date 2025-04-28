package com.mybank.authservice_my_bank.domain.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("USER",5000),
    ADMIN("ADMIN",10000),
    OWNER("OWNER",15000);

    private final String role;

    private final int creditLimit;

    @Override
    public String getAuthority() {
        return role;
    }
}
