package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import java.util.List;

public interface CrawlingProductStoragePort {

    List<CrawlingProduct> findByRegDateTimeAndCategory(LocalDateTime start, LocalDateTime end,
        Category category);

    void saveAll(List<CrawlingProduct> products);

    boolean existsByShopCodeAndProductId(String shopCode, String productId);
}
