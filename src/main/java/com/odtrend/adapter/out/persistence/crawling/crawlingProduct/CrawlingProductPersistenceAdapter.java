package com.odtrend.adapter.out.persistence.crawling.crawlingProduct;

import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(transactionManager = "crawlingTransactionManager")
class CrawlingProductPersistenceAdapter implements CrawlingProductStoragePort {

    private final CrawlingProductRepository crawlingProductRepository;

    @Override
    public List<CrawlingProduct> findByRegDateTime(LocalDateTime start, LocalDateTime end) {
        return crawlingProductRepository.findByRegDateTimeBetween(start, end)
            .stream()
            .map(CrawlingProductEntity::toDomain)
            .toList();
    }

    @Override
    public void saveAll(List<CrawlingProduct> products) {
        crawlingProductRepository.saveAll(
            products.stream()
                .map(domain -> {
                    CrawlingProductEntity entity = CrawlingProductEntity.of(domain);
                    entity.updateFieldSize();
                    return entity;
                })
                .toList()
        );
    }

    @Override
    public boolean existsByShopCodeAndProductId(String ShopCode, String productId) {
        return crawlingProductRepository.existsByShopCodeAndProductId(ShopCode, productId);
    }
}
