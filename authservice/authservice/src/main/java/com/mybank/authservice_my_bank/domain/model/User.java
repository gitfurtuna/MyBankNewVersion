package com.mybank.authservice_my_bank.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.*;
import java.sql.Blob;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{

        @Email
        @NotEmpty
        private String email;

        @NotEmpty
        private String password;

        @NotEmpty
        private String name;

        @NotEmpty
        private String surname;

        @NotEmpty
        private LocalDate dateOfBirth;

        @NotEmpty
        private String phoneNumber;

        @NotEmpty
        @Builder.Default
        private LocalDateTime dateTimeOfCreated = LocalDateTime.now();

        @NotEmpty
        @Builder.Default
        private LocalDateTime dateTimeOfLastEnter = LocalDateTime.now();

        @NotEmpty
        @Builder.Default
        private int accountAmount = Role.USER.getCreditLimit();

        @NotEmpty
        @Builder.Default
        private String role = Role.USER.getAuthority();

        @Setter(AccessLevel.NONE)
        private long countAttendanceDays;

        @Builder.Default
        private Photo photo = null;


    public long getCountAttendanceDays() {
        return countAttendanceDays();
    }

    private long countAttendanceDays() {
        return Duration.between(dateTimeOfCreated.toLocalDate().atStartOfDay(),
                dateTimeOfLastEnter.toLocalDate().atStartOfDay()).toDays();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

