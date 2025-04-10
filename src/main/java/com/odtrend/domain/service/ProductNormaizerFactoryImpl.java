package com.odtrend.domain.service;

import static com.odtrend.infrastructure.exception.ErrorCode.Product_Nomalizer_Factory_Error;

import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class ProductNormaizerFactoryImpl implements ProductNormaizerFactory {

    private final Map<String, ProductNormalizer> factoryMap;

    ProductNormaizerFactoryImpl(List<ProductNormalizer> productNormalizers) {
        factoryMap = new HashMap<>();
        productNormalizers.forEach(productNormalizer -> factoryMap.put(
            productNormalizer.getShopCode(), productNormalizer));
    }

    @Override
    public ProductNormalizer getNormalizer(String shopCode) {
        return Optional.of(factoryMap.get(shopCode))
            .orElseThrow(() -> new CustomBusinessException(Product_Nomalizer_Factory_Error));
    }
}
