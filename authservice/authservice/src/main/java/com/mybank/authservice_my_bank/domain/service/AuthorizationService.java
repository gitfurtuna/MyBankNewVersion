package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.datasource.mapper.DataDomainUserMapper;
import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.datasource.repository.UserRepository;
import com.mybank.authservice_my_bank.domain.exception.NotFoundUserException;
import com.mybank.authservice_my_bank.domain.model.Photo;
import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.web.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    final DataDomainUserMapper dataDomainUserMapper;

    public User updatePhoto(String email, MultipartFile file) throws IOException {
        Photo photo = null;
        if (file.getSize() > 0) {
            photo = new Photo();
            photo.setName(file.getName());
            photo.setOriginalFileName(file.getOriginalFilename());
            photo.setContentType(file.getContentType());
            photo.setSize(file.getSize());
            photo.setBytes(file.getBytes());
        }
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException("User with " + email + " is not found"));
        userEntity.setPhoto(dataDomainUserMapper.fromPhoto(photo));

        return dataDomainUserMapper.toUser(userEntity);
    }

 public User updateProfile(User user) {

 }

}
