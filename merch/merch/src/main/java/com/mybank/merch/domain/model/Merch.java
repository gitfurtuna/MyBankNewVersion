package com.mybank.merch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Merch {

    private String name;

    private String imageUrl;

    private String description;

    private Long price;

    private int counter;

}