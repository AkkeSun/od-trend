package com.odtrend.domain.model;

import lombok.Getter;

@Getter
public enum ShopInfo {

    NAVER("네이버", "15"),

    ;

    private final String shopName;
    private final String shopCode;

    ShopInfo(String shopName, String shopCode) {
        this.shopName = shopName;
        this.shopCode = shopCode;
    }
}
