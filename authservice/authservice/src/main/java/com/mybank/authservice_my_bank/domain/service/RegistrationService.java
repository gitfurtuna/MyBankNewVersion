package com.mybank.authservice_my_bank.domain.service;

import com.mybank.authservice_my_bank.datasource.mapper.DataDomainUserMapper;
import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.exception.UserAlreadyExistException;
import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.datasource.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RegistrationService {

    private final DataDomainUserMapper dataDomainUserMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        System.out.println(user.getAccountAmount() + " "+ user.getDateTimeOfCreated() + " " + user.getDateTimeOfLastEnter());
        UserEntity userEntity = dataDomainUserMapper.fromUser(user);
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
          throw new UserAlreadyExistException("Registration failed: user with mail " + user.getEmail() +" already exist!");
        }
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        return dataDomainUserMapper.toUser(userRepository.save(userEntity));
    }

}
