package com.training.springbootusecase.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.training.springbootusecase.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.springbootusecase.dto.AccountInfoDto;
import com.training.springbootusecase.dto.BeneficiaryDto;
import com.training.springbootusecase.dto.CredentialDto;
import com.training.springbootusecase.dto.RequestDto;
import com.training.springbootusecase.exceptions.AuthenticationException;
import com.training.springbootusecase.exceptions.DuplicateUserException;
import com.training.springbootusecase.exceptions.NoSuchAccountException;
import com.training.springbootusecase.exceptions.NotSufficientFundException;
import com.training.springbootusecase.repository.AccountRepository;
import com.training.springbootusecase.repository.ConnectionsRepository;
import com.training.springbootusecase.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConnectionsRepository connectionsRepository;

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public Account getAccountById(long accountId) {
        if (repository.existsById(accountId))
            return repository.getById(accountId);
        else {
            log.info("Account doesn't exist");
            throw new NoSuchAccountException("Account cannot be found");
        }
    }

    public User getUserByEmail(String email) {
        if (userRepository.existsUserByEmail(email))
            return userRepository.findByEmail(email);
        else {
            log.info("Account doesn't exist");
            throw new NoSuchAccountException("Account cannot be found");
        }
    }

    public String fundTransfer(long fromAccountId, long toAccountId, double amount) {
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);
        if (fromAccount.getBalance() > amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
        } else {
            log.info("Insufficient Balance");
            throw new NotSufficientFundException("You have low balance. You can't make this transaction");
        }
        toAccount.setBalance(toAccount.getBalance() + amount);
        TransactionHistory history = TransactionHistory.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amountDebited(amount)
                .balance(fromAccount.getBalance())
                .time(LocalDateTime.now())
                .build();
        fromAccount.setHistory(history);
        repository.save(fromAccount);
        repository.save(toAccount);
        return "transaction successful";
    }

    public BeneficiaryDto addBeneficiary(RequestDto dto) {
        Account account = getAccountById(dto.getAccountId());
        BeneficiaryConnections connections = BeneficiaryConnections.builder()
                .account(account)
                .beneficiaryUserName(dto.getBeneficiaryUserName())
                .beneficiaryPersonAccountId(dto.getBeneficiaryPersonAccountId())
                .build();
        connectionsRepository.save(connections);
        return BeneficiaryDto.builder()
                .accountId(account.getAccountId())
                .beneficiaryId(connections.getBeneficiaryPersonAccountId())
                .build();
    }

    public CredentialDto addUser(String name, String email, String password, GenderType gender,
                                 AccountType accountType, double balance) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateUserException("User already registered");
        User user = User.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .accountType(accountType)
                .password(password)
                .build();
        userRepository.save(user);
        Account account = Account.builder()
                .user(user)
                .balance(balance)
                .effectiveDate(LocalDate.now())
                .build();
        repository.save(account);
        return CredentialDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

    }

    public AccountInfoDto login(String email,String password) {
        User user = getUserByEmail(email);
        Account account = repository.findByUser(user);
        if (user.getPassword().equals(password))
            return AccountInfoDto.builder()
                    .email(user.getEmail())
                    .balance(account.getBalance())
                    .userName(user.getName())
                    .build();
        else {
            log.info("Authentication failed");
            throw new AuthenticationException("Password is wrong");
        }
    }
    public long findAccountByUserMail(String email){
        User user = getUserByEmail(email);
        Account account = repository.findByUser(user);
        return account.getAccountId();
    }
}
