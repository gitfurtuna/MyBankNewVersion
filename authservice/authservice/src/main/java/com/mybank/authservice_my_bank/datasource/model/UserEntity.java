package com.mybank.authservice_my_bank.datasource.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Setter(AccessLevel.NONE)
        @Column(name = "user_id", nullable = false)
        private Long id;

        @Column(name = "user_email", unique = true, nullable = false)
        private String email;

        @Column(name = "user_password", nullable = false)
        private String password;

        @Column(name = "user_name", nullable = false)
        private String name;

        @Column(name = "user_surname", nullable = false)
        private String surname;

        @Column(name = "user_date_of_birth", nullable = false)
        private LocalDate dateOfBirth;

        @Column(name = "user_phone_number", nullable = false)
        private String phoneNumber;

        @Column(name = "user_date_of_registration", nullable = false)
        private LocalDateTime dateTimeOfCreated;

        @Column(name = "user_date_of_last_enter", nullable = false)
        private LocalDateTime dateTimeOfLastEnter;

        @Column(name = "user_balance", nullable = false)
        private int accountAmount;

        @Column(name = "user_role", nullable = false)
        private String role;

}
