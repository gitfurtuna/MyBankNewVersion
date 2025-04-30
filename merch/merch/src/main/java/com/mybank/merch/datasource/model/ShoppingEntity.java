package com.mybank.merch.datasource.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long shoppingId;

    @Column(nullable = false)
    private Long merchId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long totalPrice;

}
