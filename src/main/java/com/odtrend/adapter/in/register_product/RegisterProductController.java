package com.odtrend.adapter.in.register_product;

import com.odtrend.applicaiton.port.in.RegisterProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterProductController {

    private final RegisterProductUseCase registerProductUseCase;

    @PostMapping("/products")
    void registerProducts() {
        registerProductUseCase.registerProduct();
    }
}
