
package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.domain.model.JwtAuthentication;
import com.mybank.authservice_my_bank.domain.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setRole(Role.valueOf(claims.get("role", String.class)));
        jwtAuthentication.setName(claims.get("name", String.class));
        jwtAuthentication.setEmail(claims.getSubject());
        return jwtAuthentication;
    }

}
