package com.odtrend.adapter.out.persistence.crawlingProduct;

import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.domain.model.CrawlingProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CrawlingProductPersistenceAdapter implements CrawlingProductStoragePort {

    private final CrawlingProductMapper mapper;
    private final CrawlingProductRepository crawlingProductRepository;

    @Override
    public void saveAll(List<CrawlingProduct> products) {
        crawlingProductRepository.saveAll(
            products.stream()
                .map(domain -> {
                    CrawlingProductEntity entity = mapper.toEntity(domain);
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
