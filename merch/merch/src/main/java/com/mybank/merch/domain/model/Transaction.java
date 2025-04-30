package com.mybank.merch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Transaction {

 private UUID transactionID;

 private Long userId;

 private Long ownerId;

 private Long coins;

}
