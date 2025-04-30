package com.mybank.authservice_my_bank.datasource.mapper;

import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T14:36:47+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Amazon.com Inc.)"
)
@Component
public class DataDomainUserMapperImpl implements DataDomainUserMapper {

    @Override
    public UserEntity fromUser(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.email( user.getEmail() );
        userEntity.password( user.getPassword() );
        userEntity.name( user.getName() );
        userEntity.surname( user.getSurname() );
        userEntity.dateOfBirth( user.getDateOfBirth() );
        userEntity.phoneNumber( user.getPhoneNumber() );
        userEntity.dateTimeOfCreated( user.getDateTimeOfCreated() );
        userEntity.dateTimeOfLastEnter( user.getDateTimeOfLastEnter() );
        userEntity.accountAmount( user.getAccountAmount() );
        userEntity.role( user.getRole() );

        return userEntity.build();
    }

    @Override
    public User toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( userEntity.getEmail() );
        user.password( userEntity.getPassword() );
        user.name( userEntity.getName() );
        user.surname( userEntity.getSurname() );
        user.dateOfBirth( userEntity.getDateOfBirth() );
        user.phoneNumber( userEntity.getPhoneNumber() );
        user.dateTimeOfCreated( userEntity.getDateTimeOfCreated() );
        user.dateTimeOfLastEnter( userEntity.getDateTimeOfLastEnter() );
        user.accountAmount( userEntity.getAccountAmount() );
        user.role( userEntity.getRole() );

        return user.build();
    }
}
