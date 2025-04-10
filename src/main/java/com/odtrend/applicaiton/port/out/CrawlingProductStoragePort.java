package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.CrawlingProduct;
import java.util.List;

public interface CrawlingProductStoragePort {

    void saveAll(List<CrawlingProduct> products);

    boolean existsByShopCodeAndProductId(String ShopCode, String productId);
}
