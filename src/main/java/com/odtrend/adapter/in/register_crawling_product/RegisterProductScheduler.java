package com.odtrend.adapter.in.register_crawling_product;

import com.odtrend.applicaiton.port.in.RegisterCrawlingProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterProductScheduler {

    private final RegisterCrawlingProductUseCase registerProductUseCase;

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    void registerProducts() {
        registerProductUseCase.registerProduct();
    }
}
