package com.odtrend.adapter.out.persistence.crawlingProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlingProductRepository extends JpaRepository<CrawlingProductEntity, Long> {

    boolean existsByShopCodeAndProductId(String shopCode, String productId);
}
