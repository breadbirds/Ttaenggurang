package com.ladysparks.ttaenggrang.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"id"}, allowGetters=true)
public class TaxCreateDTO {

    private Long id;

    @NotEmpty(message = "세금 이름을 입력하세요.")
    private String taxName;

    @NotNull(message = "세율을 입력하세요.")
    @DecimalMin(value = "0.00", inclusive = false, message = "세율은 0보다 커야 합니다.")
    @DecimalMax(value = "1.00", inclusive = false, message = "세율은 1보다 작아야 합니다.")
    private BigDecimal taxRate;

    @NotEmpty(message = "세금 설명을 입력하세요.")
    private String taxDescription;
}
