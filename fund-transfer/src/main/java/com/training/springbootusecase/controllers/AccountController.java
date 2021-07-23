package com.training.springbootusecase.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.springbootusecase.dto.AccountInfoDto;
import com.training.springbootusecase.dto.BeneficiaryDto;
import com.training.springbootusecase.dto.CredentialDto;
import com.training.springbootusecase.dto.RequestDto;
import com.training.springbootusecase.entity.AccountType;
import com.training.springbootusecase.entity.GenderType;
import com.training.springbootusecase.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("fund-transfer")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/{name}/{email}/{password}/{gender}/{accountType}/{balance}")
    public ResponseEntity<CredentialDto> register(@PathVariable("name") String name,
                                                  @PathVariable("email") String email,
                                                  @PathVariable("password") String password,
                                                  @PathVariable("gender") GenderType gender,
                                                  @PathVariable("accountType") AccountType accountType,
                                                  @PathVariable("balance") double balance) {
        CredentialDto dto = service.addUser(name,email,password,gender,accountType,balance);
        log.info("User with name " + name + " is registered");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Long> findAccountByUserEmail(@PathVariable String email){
        log.info("searching user : "+email);
        return new ResponseEntity<>(service.findAccountByUserMail(email),HttpStatus.OK);
    }

    @PostMapping("/{request}")
    public ResponseEntity<BeneficiaryDto> addBeneficary(@RequestBody RequestDto request) {
        BeneficiaryDto dto = service.addBeneficiary(request);
        log.info("Beneficiary account added for " + dto.getAccountId());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<String> fundTransfer(@PathVariable("fromAccountId") long fromAccountId,
                                               @PathVariable("toAccountId") long toAccountId,
                                               @PathVariable("amount") double amount) {
        String message = service.fundTransfer(fromAccountId,toAccountId,amount);
        log.info("Your account is debited with " + amount);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/{email}/{password}")
    public ResponseEntity<AccountInfoDto> login(@PathVariable("email") String email, @PathVariable("password") String password) {

        AccountInfoDto dto = service.login(email,password);
        log.info("logged in");
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

}
