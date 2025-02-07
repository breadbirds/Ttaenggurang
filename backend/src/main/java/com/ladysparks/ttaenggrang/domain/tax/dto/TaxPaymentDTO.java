package com.ladysparks.ttaenggrang.domain.tax.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.tax.entity.TaxPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"id", "createdAt"}, allowGetters = true)
public class TaxPaymentDTO {

    private Long id;
    private Long studentId;
    private Long taxId;
    private int amount;
    private Date paymentDate;
    private TaxPaymentStatus status;
    private Timestamp createdAt;

}
