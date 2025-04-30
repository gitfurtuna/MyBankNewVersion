package com.mybank.authservice_my_bank.web.mapper;

import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.web.model.AuthJWTRequest;
import com.mybank.authservice_my_bank.web.model.RegistrationRequest;
import com.mybank.authservice_my_bank.web.model.RegistrationResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T14:36:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class WebDomainUserMapperImpl implements WebDomainUserMapper {

    @Override
    public User toUserFromAuth(AuthJWTRequest authJWTRequest) {
        if ( authJWTRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( authJWTRequest.getEmail() );
        user.password( authJWTRequest.getPassword() );

        return user.build();
    }

    @Override
    public RegistrationResponse fromUser(User user) {
        if ( user == null ) {
            return null;
        }

        RegistrationResponse.RegistrationResponseBuilder registrationResponse = RegistrationResponse.builder();

        registrationResponse.name( user.getName() );
        registrationResponse.email( user.getEmail() );

        return registrationResponse.build();
    }

    @Override
    public User toUserFromRegister(RegistrationRequest registrationRequest) {
        if ( registrationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( registrationRequest.getEmail() );
        user.password( registrationRequest.getPassword() );
        user.name( registrationRequest.getName() );
        user.surname( registrationRequest.getSurname() );
        user.dateOfBirth( registrationRequest.getDateOfBirth() );
        user.phoneNumber( registrationRequest.getPhoneNumber() );

        return user.build();
    }
}
