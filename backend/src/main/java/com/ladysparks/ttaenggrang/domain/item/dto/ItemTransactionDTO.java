package com.ladysparks.ttaenggrang.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTransactionDTO {

    private Long id;
    private Long itemId;
    private Long buyerId;
    private int quantity;
    private Timestamp createdAt;

}
