package com.mybank.merch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
public class Merch {

    private String name;

    private String imageUrl;

    private String description;

    private Long price;

    private int counter;

}