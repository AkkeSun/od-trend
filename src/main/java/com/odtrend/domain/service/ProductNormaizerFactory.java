package com.odtrend.domain.service;

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
        return BY_SHOP_CODE.get(shopCode).normalizer;
    }

    private static final Map<String, ProductNormaizerFactory> BY_SHOP_CODE =
        Stream.of(values()).collect(Collectors.toMap(ProductNormaizerFactory::shopCOde, e -> e));
}
