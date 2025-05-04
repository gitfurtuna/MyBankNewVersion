package com.mybank.authservice_my_bank.web.mapper;

import com.mybank.authservice_my_bank.web.model.AuthJWTRequest;
import com.mybank.authservice_my_bank.web.model.RegistrationRequest;
import com.mybank.authservice_my_bank.web.model.RegistrationResponse;
import com.mybank.authservice_my_bank.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebDomainUserMapper {
    User toUserFromAuth (AuthJWTRequest authJWTRequest);

    RegistrationResponse fromUser (User user);

    User toUserFromRegister(RegistrationRequest registrationRequest);

    RegistrationRequest fromUserToRegistrationRequest(User user);
}
