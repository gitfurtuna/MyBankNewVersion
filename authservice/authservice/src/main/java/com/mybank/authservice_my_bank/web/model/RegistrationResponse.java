package com.mybank.authservice_my_bank.web.model;

import lombok.*;

@Getter
@Setter
@Builder
public class RegistrationResponse {

    private String name;

    private String email;
}
