package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import java.util.List;

public interface CrawlingProductStoragePort {

    List<CrawlingProduct> findByRegDateTime(LocalDateTime start, LocalDateTime end);

    void saveAll(List<CrawlingProduct> products);

    boolean existsByShopCodeAndProductId(String ShopCode, String productId);
}
