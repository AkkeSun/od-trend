package com.odtrend.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShopInfo {

    private String url;
    private String method;
    private String body;

    @Builder
    public ShopInfo(String url, String method, String body) {
        this.url = url;
        this.method = method;
        this.body = body;
    }
}
