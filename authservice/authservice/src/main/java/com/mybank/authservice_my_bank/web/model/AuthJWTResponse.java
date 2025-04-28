
package com.mybank.authservice_my_bank.web.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthJWTResponse {

        private final String type = "Bearer";

        private String accessToken;

        private String refreshToken;

    }
