package com.mybank.authservice_my_bank.datasource.data;

import com.mybank.authservice_my_bank.datasource.mapper.DataDomainUserMapper;
import com.mybank.authservice_my_bank.datasource.model.UserEntity;
import com.mybank.authservice_my_bank.domain.model.User;
import com.mybank.authservice_my_bank.domain.model.Role;
import com.mybank.authservice_my_bank.datasource.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final DataDomainUserMapper dataDomainUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User adminUser = User.builder()
                .name("admin_name")
                .surname("admin_surname")
                .email("admin_email@example.com")
                .password(passwordEncoder.encode("admin_password"))
                .role(Role.ADMIN.getAuthority())
                .accountAmount(Role.ADMIN.getCreditLimit())
                .dateOfBirth(LocalDate.of(1970, 11,11))
                .phoneNumber("7-777-777-777")
                .dateTimeOfCreated(LocalDateTime.now())
                .dateTimeOfLastEnter(LocalDateTime.now())
                .build();
        UserEntity userAdmin = dataDomainUserMapper.fromUser(adminUser);
        userRepository.save(userAdmin);

        User ownerUser = User.builder()
                .name("owner_name")
                .surname("owner_surname")
                .email("owner_email@example.com")
                .password(passwordEncoder.encode("owner_password"))
                .role(Role.OWNER.getAuthority())
                .accountAmount(Role.OWNER.getCreditLimit())
                .dateOfBirth(LocalDate.of(1980, 10,10))
                .phoneNumber("8-888-888-888")
                .dateTimeOfCreated(LocalDateTime.now())
                .dateTimeOfLastEnter(LocalDateTime.now())
                .build();
        UserEntity userOwner = dataDomainUserMapper.fromUser(ownerUser);
        userRepository.save(userOwner);

        User userUser = User.builder()
                .name("user_name")
                .surname("user_surname")
                .email("user_email@example.com")
                .password(passwordEncoder.encode("user_password"))
                .role(Role.USER.getAuthority())
                .accountAmount(Role.USER.getCreditLimit())
                .dateOfBirth(LocalDate.of(1990, 1,1))
                .phoneNumber("9-999-999-999")
                .dateTimeOfCreated(LocalDateTime.now())
                .dateTimeOfLastEnter(LocalDateTime.now())
                .build();
        UserEntity userUserE = dataDomainUserMapper.fromUser(userUser);
        userRepository.save(userUserE);
    }
}
