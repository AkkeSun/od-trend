package com.odtrend.adapter.in.register_trend_product;

import com.odtrend.applicaiton.port.in.RegisterTrendProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterTrendProductScheduler {

    private final RegisterTrendProductUseCase useCase;

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    void registerProducts() {
        useCase.register();
    }
}
