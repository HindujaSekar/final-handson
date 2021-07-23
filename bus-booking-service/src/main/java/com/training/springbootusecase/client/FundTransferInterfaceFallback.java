package com.training.springbootusecase.client;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.OK;

@Component
@Slf4j
public class FundTransferInterfaceFallback implements  FundTransferInterface{

    private Throwable cause;
    public FundTransferInterfaceFallback() {
    }
    public FundTransferInterfaceFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<String> fundTransfer(long fromAccountId, long toAccountId, double amount) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("Fund transfer API is down. Please try after some time "+ cause.getLocalizedMessage());
        }
        return new ResponseEntity<>("",OK);
    }

    @Override
    public ResponseEntity<Long> findAccountByUserEmail(String email) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("Fund transfer API is down. Please try after some time "+ cause.getLocalizedMessage());
        }
        return new ResponseEntity<>(0L,OK);
    }
}
