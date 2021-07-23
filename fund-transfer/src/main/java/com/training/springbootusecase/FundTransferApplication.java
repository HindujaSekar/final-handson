package com.training.springbootusecase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FundTransferApplication  {

	public static void main(String[] args) {
		SpringApplication.run(FundTransferApplication.class, args);
	}

}
