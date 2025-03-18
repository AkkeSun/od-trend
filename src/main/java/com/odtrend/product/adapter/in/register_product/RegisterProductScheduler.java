package com.odtrend.product.adapter.in.register_product;

import com.odtrend.product.applicaiton.port.out.ProductApiPort;
import com.odtrend.product.domain.ShopInfo;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Component;

@Component
class RegisterProductScheduler implements ProductApiPort {

    private final ClientHttpRequestFactorySettings settings;

    RegisterProductScheduler(ClientHttpRequestFactorySettings settings) {
        this.settings = settings;
    }

    @Override
    public String getProducts(ShopInfo shopInfo) {
        return null;
    }
}
