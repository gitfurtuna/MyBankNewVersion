package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.domain.model.JwtAuthentication;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";

    private final JwtProviderService jwtProviderService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
           if (token != null && jwtProviderService.validateAccessToken(token)) {
               final Claims claims = jwtProviderService.getAccessClaims(token);
               final JwtAuthentication jwtAuthentication = JwtUtils.generate(claims);
               jwtAuthentication.setAuthenticated(true);
               SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
           }
           filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
