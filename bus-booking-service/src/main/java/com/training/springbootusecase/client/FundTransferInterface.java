package com.training.springbootusecase.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "http://FUND-TRANSFER-APPLICATION/fund-transfer", fallbackFactory = FundTransferInterfaceFallbackFactory.class)
public interface FundTransferInterface {

	@PostMapping("/{fromAccountId}/{toAccountId}/{amount}")
	public ResponseEntity<String> fundTransfer(@PathVariable("fromAccountId") long fromAccountId,
											   @PathVariable("toAccountId") long toAccountId,
											   @PathVariable("amount") double amount);
	@GetMapping("/{email}")
	public ResponseEntity<Long> findAccountByUserEmail(@PathVariable String email);
	
}
