package com.ladysparks.ttaenggrang.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NationCreateDTO {

    private Long id;

    @NotBlank(message = "국가 이름은 필수 항목입니다.")
    private String nationName;

    @NotNull(message = "설립일을 입력해야 합니다.")
    private Timestamp establishedDate;

    @Min(value = 1, message = "인구는 양의 정수여야 합니다.")
    private Integer population;

    @NotBlank(message = "통화 단위는 필수 항목입니다.")
    private String currency;
}
