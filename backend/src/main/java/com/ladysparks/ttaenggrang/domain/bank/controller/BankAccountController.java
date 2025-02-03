package com.ladysparks.ttaenggrang.domain.bank.controller;

import com.ladysparks.ttaenggrang.global.docs.BankAccountApiSpecification;
import com.ladysparks.ttaenggrang.domain.bank.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.domain.bank.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController implements BankAccountApiSpecification {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // 은행 계좌 [등록]
    @PostMapping
    public ResponseEntity<ApiResponse<BankAccountDTO>> BankAccountAdd(@RequestBody BankAccountDTO bankAccountDto) {
        BankAccountDTO savedBankAccountDto = bankAccountService.addBankAccount(bankAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(savedBankAccountDto));
    }

    // 은행 계좌 [조회]
    @GetMapping("/{bankAccountId}")
    public ResponseEntity<ApiResponse<BankAccountDTO>> BankAccountDetails(@PathVariable("bankAccountId") Long bankAccountId) {
        BankAccountDTO account = bankAccountService.findBankAccount(bankAccountId);
        return ResponseEntity.ok(ApiResponse.success(account));
    }

}
