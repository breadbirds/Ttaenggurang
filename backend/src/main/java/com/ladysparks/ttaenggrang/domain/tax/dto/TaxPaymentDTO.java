package com.ladysparks.ttaenggrang.domain.tax.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.tax.entity.TaxPaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"id", "studentId", "paymentDate", "createdAt"}, allowGetters = true)
public class TaxPaymentDTO {

    private Long id;

    private Long studentId;

    @NotNull(message = "세금 ID(taxId)는 필수 항목입니다.")
    private Long taxId;

    @NotNull(message = "납부 금액(amount)은 필수 항목입니다.")
    @Min(value = 1, message = "납부 금액(amount)은 1 이상이어야 합니다.")
    private int amount;

    private LocalDate paymentDate;

    @NotNull(message = "납부 상태(status)는 필수 항목입니다.")
    private TaxPaymentStatus status;

    private Timestamp createdAt;

}
