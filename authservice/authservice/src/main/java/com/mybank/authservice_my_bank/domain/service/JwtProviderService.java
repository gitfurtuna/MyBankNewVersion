package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.domain.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Slf4j
@Service
public class JwtProviderService {

    private final PublicKey jwtAccessPublicKey;

    private final PrivateKey jwtAccessPrivateKey;

    private final PublicKey jwtRefreshPublicKey;

    private final PrivateKey jwtRefreshPrivateKey;

    public JwtProviderService(@Value("${jwt.secret.public.access}") String jwtAccessPublicKey,
                              @Value("${jwt.secret.private.access}") String jwtAccessPrivateKey,
                              @Value("${jwt.secret.public.refresh}") String jwtRefreshPublicKey,
                              @Value("${jwt.secret.private.refresh}") String jwtRefreshPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] accessPublicBytes = Decoders.BASE64.decode(jwtAccessPublicKey);
        X509EncodedKeySpec accessPublicSpec = new X509EncodedKeySpec(accessPublicBytes);
        this.jwtAccessPublicKey = keyFactory.generatePublic(accessPublicSpec);

        byte[] accessPrivateBytes = Decoders.BASE64.decode(jwtAccessPrivateKey);
        PKCS8EncodedKeySpec accessPrivateSpec = new PKCS8EncodedKeySpec(accessPrivateBytes);
        this.jwtAccessPrivateKey = keyFactory.generatePrivate(accessPrivateSpec);

        byte[] refreshPublicBytes = Decoders.BASE64.decode(jwtRefreshPublicKey);
        X509EncodedKeySpec refreshPublicSpec = new X509EncodedKeySpec(refreshPublicBytes);
        this.jwtRefreshPublicKey = keyFactory.generatePublic(refreshPublicSpec);

        byte[] refreshPrivateBytes = Decoders.BASE64.decode(jwtRefreshPrivateKey);
        PKCS8EncodedKeySpec refreshPrivateSpec = new PKCS8EncodedKeySpec(refreshPrivateBytes);
        this.jwtRefreshPrivateKey = keyFactory.generatePrivate(refreshPrivateSpec);

    }


    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(accessExpiration)
                .signWith(jwtAccessPrivateKey)
                .claim("role", user.getRole())
                .claim("name", user.getName())
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(refreshExpiration)
                .signWith(jwtRefreshPrivateKey)
                .compact();
    }


    public boolean validateAccessToken(@NotEmpty String token) {
        return validateToken(token, jwtAccessPublicKey);
    }


    public boolean validateRefreshToken(@NotEmpty String token) {
        return validateToken(token, jwtRefreshPublicKey);
    }


    private boolean validateToken(@NotEmpty String token, @NonNull PublicKey publicKey) {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported jwt", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed jwt", e);
        } catch (JwtException e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NotEmpty String token) {
        return getClaims(token, jwtAccessPublicKey);
    }

    public Claims getRefreshClaims(@NotEmpty String token) {
        return getClaims(token, jwtRefreshPublicKey);
    }


    private Claims getClaims(@NotEmpty String token, @NonNull PublicKey publicKey) {
        return  Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
