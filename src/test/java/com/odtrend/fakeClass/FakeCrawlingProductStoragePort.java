package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeCrawlingProductStoragePort implements CrawlingProductStoragePort {

    public List<CrawlingProduct> database = new ArrayList<>();

    @Override
    public List<CrawlingProduct> findByRegDateTime(LocalDateTime start, LocalDateTime end) {
        return database.stream()
            .filter(product -> product.getRegDateTime().isAfter(start.minusSeconds(1)) &&
                product.getRegDateTime().isBefore(end.plusSeconds(1)))
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
