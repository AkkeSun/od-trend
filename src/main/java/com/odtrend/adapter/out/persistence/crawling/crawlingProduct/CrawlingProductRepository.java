package com.odtrend.adapter.out.persistence.crawling.crawlingProduct;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface CrawlingProductRepository extends JpaRepository<CrawlingProductEntity, Long> {

    boolean existsByShopCodeAndProductId(String shopCode, String productId);

    List<CrawlingProductEntity> findByRegDateTimeBetween(LocalDateTime start,
        LocalDateTime end);

}
