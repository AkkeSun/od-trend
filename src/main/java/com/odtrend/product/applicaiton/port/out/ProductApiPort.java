package com.odtrend.product.applicaiton.port.out;

import com.odtrend.product.domain.ShopInfo;

public interface ProductApiPort {

    String getProducts(ShopInfo shopInfo);
}
