package com.ladysparks.ttaenggrang.controller;

import com.ladysparks.ttaenggrang.docs.BankAccountApiSpecification;
import com.ladysparks.ttaenggrang.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.service.BankAccountService;
import jakarta.persistence.EntityNotFoundException;
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

    //
    @GetMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountDTO> BankAccountDetails(@PathVariable("bankAccountId") Long bankAccountId) {
        BankAccountDTO bankAccount = bankAccountService.findBankAccount(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found with id: " + bankAccountId));

        return ResponseEntity.ok(bankAccount);

//        return bankAccountService.findById(studentId)
//                .map(ResponseEntity::ok) // ✅ 값이 있으면 200 OK + DTO 반환
//                .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ 값이 없으면 404 Not Found 반환
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> BankAccountAdd(@RequestBody BankAccountDTO bankAccountDto) {
        BankAccountDTO savedDto = bankAccountService.addBankAccount(bankAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto); // ✅ 201 Created 반환
    }

}
