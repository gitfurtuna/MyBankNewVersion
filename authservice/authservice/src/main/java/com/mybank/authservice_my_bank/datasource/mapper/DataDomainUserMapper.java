package com.mybank.authservice_my_bank.datasource.mapper;

import com.mybank.authservice_my_bank.datasource.model.PhotoEntity;
import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.model.Photo;
import com.mybank.authservice_my_bank.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataDomainUserMapper {

    UserEntity fromUser(User user);
    User toUser (UserEntity userEntity);

    PhotoEntity fromPhoto(Photo photo);
    Photo toPhoto(PhotoEntity photoEntity);
}
