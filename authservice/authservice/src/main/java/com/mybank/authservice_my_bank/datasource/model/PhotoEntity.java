package com.mybank.authservice_my_bank.datasource.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    @Lob
    private byte[] bytes;
}
