package com.mybank.authservice_my_bank.datasource.mapper;

import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataDomainUserMapper {

    UserEntity fromUser(User user);

    User toUser (UserEntity userEntity);
}
