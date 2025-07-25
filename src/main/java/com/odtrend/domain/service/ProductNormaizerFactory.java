package com.odtrend.domain.service;

import static com.odtrend.infrastructure.exception.ErrorCode.Product_Nomalizer_Factory_Error;

import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductNormaizerFactory {

    NAVER("15", new NaverProductNormalizer());

    private final String shopCode;
    private final ProductNormalizer normalizer;

    private String shopCOde() {
        return shopCode;
    }

    private ProductNormalizer normalizer() {
        return normalizer;
    }

    public static ProductNormalizer getNormalizer(String shopCode) {
        try {
            return BY_SHOP_CODE.get(shopCode).normalizer;
        } catch (Exception e) {
            throw new CustomBusinessException(Product_Nomalizer_Factory_Error);
        }
    }

    private static final Map<String, ProductNormaizerFactory> BY_SHOP_CODE =
        Stream.of(values()).collect(Collectors.toMap(ProductNormaizerFactory::shopCOde, e -> e));
}
