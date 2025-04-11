package com.odtrend.adapter.out.persistence.crawlingProduct;

import com.odtrend.domain.model.CrawlingProduct;
import org.springframework.stereotype.Component;

@Component
class CrawlingProductMapper {

    public CrawlingProductEntity toEntity(CrawlingProduct crawlingProduct) {
        return CrawlingProductEntity.builder()
            .id(crawlingProduct.getId())
            .transactionId(crawlingProduct.getTransactionId())
            .shopCode(crawlingProduct.getShopCode())
            .productId(crawlingProduct.getProductId())
            .productName(crawlingProduct.getProductName())
            .price(crawlingProduct.getPrice())
            .imgUrl(crawlingProduct.getImgUrl())
            .productUrl(crawlingProduct.getProductUrl())
            .description(crawlingProduct.getDescription())
            .category(crawlingProduct.getCategory())
            .regDateTime(crawlingProduct.getRegDateTime())
            .build();
    }

    public CrawlingProduct toDomain(CrawlingProductEntity crawlingProductEntity) {
        return CrawlingProduct.builder()
            .id(crawlingProductEntity.getId())
            .transactionId(crawlingProductEntity.getTransactionId())
            .shopCode(crawlingProductEntity.getShopCode())
            .productId(crawlingProductEntity.getProductId())
            .productName(crawlingProductEntity.getProductName())
            .price(crawlingProductEntity.getPrice())
            .imgUrl(crawlingProductEntity.getImgUrl())
            .productUrl(crawlingProductEntity.getProductUrl())
            .description(crawlingProductEntity.getDescription())
            .category(crawlingProductEntity.getCategory())
            .regDateTime(crawlingProductEntity.getRegDateTime())
            .build();
    }
}
