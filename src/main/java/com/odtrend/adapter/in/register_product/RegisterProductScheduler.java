package com.odtrend.adapter.in.register_product;

import com.odtrend.applicaiton.port.in.RegisterProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterProductScheduler {

    private final RegisterProductUseCase registerProductUseCase;

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    void registerProducts() {
        registerProductUseCase.registerProduct();
    }
}
