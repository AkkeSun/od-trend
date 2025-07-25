package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeCrawlingProductStoragePort implements CrawlingProductStoragePort {

    public List<CrawlingProduct> database = new ArrayList<>();

    @Override
    public List<CrawlingProduct> findByRegDateTimeAndCategory(LocalDateTime start,
        LocalDateTime end, Category category) {
        return database.stream()
            .filter(product -> product.getRegDateTime().isAfter(start.minusSeconds(1)))
            .filter(product -> product.getRegDateTime().isBefore(end.plusSeconds(1)))
            .filter(product -> product.getCategory().equals(category))
            .toList();
    }

    @Override
    public void saveAll(List<CrawlingProduct> products) {
        database.addAll(products);
    }

    @Override
    public boolean existsByShopCodeAndProductId(String shopCode, String productId) {
        return !database.stream()
            .filter(product -> product.getShopCode().equals(shopCode))
            .filter(product -> product.getProductId().equals(productId))
            .toList().isEmpty();
    }
}
