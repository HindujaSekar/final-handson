package com.training.springbootusecase.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FundTransferInterfaceFallbackFactory implements FallbackFactory<FundTransferInterface> {
    @Override
    public FundTransferInterface create(Throwable throwable) {
        return new FundTransferInterfaceFallback(throwable);
    }
}
