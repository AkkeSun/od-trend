package com.odtrend.adapter.out.persistence.crawling.crawlingProduct;


import com.odtrend.domain.model.Category;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface CrawlingProductRepository extends JpaRepository<CrawlingProductEntity, Long> {

    boolean existsByShopCodeAndProductId(String shopCode, String productId);

    List<CrawlingProductEntity> findByRegDateTimeBetweenAndCategory(LocalDateTime start,
        LocalDateTime end, Category category);

}
