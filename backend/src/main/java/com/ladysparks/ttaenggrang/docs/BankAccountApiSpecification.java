package com.ladysparks.ttaenggrang.docs;

import com.ladysparks.ttaenggrang.dto.BankAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Bank-Account", description = "은행 계좌 관련 API")
public interface BankAccountApiSpecification {

    @Operation(summary = "은행 계좌 [조회]", description = "학생 ID로 은행 계좌를 조회합니다.")
    ResponseEntity<BankAccountDTO> BankAccountDetails(@PathVariable("studentId") Long studentId);

    @Operation(summary = "은행 계좌 [등록]", description = "학생 계정 생성 전에 계좌 생성(→ API 하나로 합치는 게 좋을 듯)")
    ResponseEntity<BankAccountDTO> BankAccountAdd(@RequestBody BankAccountDTO bankAccountDto);

}
