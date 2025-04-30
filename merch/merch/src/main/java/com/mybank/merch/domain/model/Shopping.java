package com.mybank.merch.domain.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class Shopping {

    @Column(nullable = false)
    private Long merchId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long totalPrice;

}
