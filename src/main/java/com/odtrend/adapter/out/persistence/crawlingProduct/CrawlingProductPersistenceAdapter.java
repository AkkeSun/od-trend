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
        products.forEach(CrawlingProduct::updateFieldSize);
        crawlingProductRepository.saveAll(
            products.stream()
                .map(mapper::toEntity)
                .toList()
        );
    }

    @Override
    public boolean existsByShopCodeAndProductId(String ShopCode, String productId) {
        return crawlingProductRepository.existsByShopCodeAndProductId(ShopCode, productId);
    }
}
