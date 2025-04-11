package com.odtrend.adapter.out.persistence.crawlingProduct;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingProduct;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrawlingProductMapperTest {

    @Nested
    @DisplayName("[success] 도메인을 엔티티로 변환하는 메소드")
    class Describe_toEntity {

        @Test
        @DisplayName("[success] 도메인이 엔티티로 잘 변환되는지 확인한다.")
        void success() {
            // given
            CrawlingProduct domain = CrawlingProduct.builder()
                .id(1L)
                .transactionId("transactionId")
                .shopCode("shopCode")
                .productId("productId")
                .productName("productName")
                .price(1000)
                .imgUrl("imgUrl")
                .productUrl("productUrl")
                .description("description")
                .category(Category.DIGITAL)
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            CrawlingProductEntity entity = new CrawlingProductMapper().toEntity(domain);

            // then
            assert entity.getId().equals(domain.getId());
            assert entity.getTransactionId().equals(domain.getTransactionId());
            assert entity.getShopCode().equals(domain.getShopCode());
            assert entity.getProductId().equals(domain.getProductId());
            assert entity.getProductName().equals(domain.getProductName());
            assert entity.getPrice().equals(domain.getPrice());
            assert entity.getImgUrl().equals(domain.getImgUrl());
            assert entity.getProductUrl().equals(domain.getProductUrl());
            assert entity.getDescription().equals(domain.getDescription());
            assert entity.getCategory().equals(domain.getCategory());
            assert entity.getRegDateTime().equals(domain.getRegDateTime());
        }
    }

    @Nested
    @DisplayName("[success] 엔티티를 도메인으로 변환하는 메소드")
    class Describe_toDomain {

        @Test
        @DisplayName("[success] 엔티티가 도메인으로 잘 변환되는지 확인한다.")
        void success() {
            // given
            CrawlingProductEntity entity = CrawlingProductEntity.builder()
                .id(1L)
                .transactionId("transactionId")
                .shopCode("shopCode")
                .productId("productId")
                .productName("productName")
                .price(1000)
                .imgUrl("imgUrl")
                .productUrl("productUrl")
                .description("description")
                .category(Category.DIGITAL)
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            CrawlingProduct domain = new CrawlingProductMapper().toDomain(entity);

            // then
            assert entity.getId().equals(domain.getId());
            assert entity.getTransactionId().equals(domain.getTransactionId());
            assert entity.getShopCode().equals(domain.getShopCode());
            assert entity.getProductId().equals(domain.getProductId());
            assert entity.getProductName().equals(domain.getProductName());
            assert entity.getPrice().equals(domain.getPrice());
            assert entity.getImgUrl().equals(domain.getImgUrl());
            assert entity.getProductUrl().equals(domain.getProductUrl());
            assert entity.getDescription().equals(domain.getDescription());
            assert entity.getCategory().equals(domain.getCategory());
            assert entity.getRegDateTime().equals(domain.getRegDateTime());
        }
    }

}