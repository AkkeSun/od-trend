package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.CrawlingPage;

public interface ShopClientPort {

    String getProducts(CrawlingPage crawlingPage);
}
