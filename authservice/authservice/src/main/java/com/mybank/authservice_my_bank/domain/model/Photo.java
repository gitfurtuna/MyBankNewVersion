package com.mybank.authservice_my_bank.domain.model;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    private byte[] bytes;

}
