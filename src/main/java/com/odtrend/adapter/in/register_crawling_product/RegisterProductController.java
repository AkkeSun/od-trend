package com.odtrend.adapter.in.register_crawling_product;

import com.odtrend.applicaiton.port.in.RegisterCrawlingProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterProductController {

    private final RegisterCrawlingProductUseCase registerProductUseCase;

    @PostMapping("/products")
    void registerProducts() {
        registerProductUseCase.registerProduct();
    }
}
