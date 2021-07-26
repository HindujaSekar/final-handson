package com.training.springbootusecase.client;

import com.training.springbootusecase.exceptions.ServiceDownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class FundTransferInterfaceFallback implements  FundTransferInterface{

    @Override
    public ResponseEntity<String> fundTransfer(long fromAccountId, long toAccountId, double amount) {
        throw new ServiceDownException("fund-transfer-api is down. Please try after some time");
    }

    @Override
    public ResponseEntity<Long> findAccountByUserEmail(String email) {
        throw new ServiceDownException("fund-transfer-api is down. Please try after some time");
    }

}
