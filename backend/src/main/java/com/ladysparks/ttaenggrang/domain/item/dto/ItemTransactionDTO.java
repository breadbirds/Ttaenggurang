package com.ladysparks.ttaenggrang.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonIgnoreProperties(value = {"id", "createdAt"}, allowGetters = true)
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
