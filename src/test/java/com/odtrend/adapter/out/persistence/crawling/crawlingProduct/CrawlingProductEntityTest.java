package com.odtrend.adapter.out.persistence.crawling.crawlingProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrawlingProductEntityTest {

    @Nested
    @DisplayName("[updateFieldSize] 필드사이즈를 수정하는 메소드")
    class Describe_updateFieldSize {

        @Test
        @DisplayName("[success] productId 사이즈가 30을 초과할 때 사이즈가 수정되는지 확인한다.")
        void success() {
            // given
            CrawlingProductEntity entity = CrawlingProductEntity.builder()
                .productId("12345".repeat(7))
                .productName("test-product-name")
                .imgUrl("test-img-url")
                .productUrl("productUrl")
                .build();

            // when
            entity.updateFieldSize();

            // then
            assert entity.getProductId().length() == 30;
        }

        @Test
        @DisplayName("[success] productName 사이즈가 100을 초과할 때 사이즈가 수정되는지 확인한다.")
        void success2() {
            // given
            CrawlingProductEntity entity = CrawlingProductEntity.builder()
                .productId("test-product-id")
                .productName("12345".repeat(21))
                .imgUrl("test-img-url")
                .productUrl("productUrl")
                .build();

            // when
            entity.updateFieldSize();

            // then
            assert entity.getProductName().length() == 100;
        }

        @Test
        @DisplayName("[success] imgUrl 사이즈가 100을 초과할 때 사이즈가 수정되는지 확인한다.")
        void success3() {
            // given
            CrawlingProductEntity entity = CrawlingProductEntity.builder()
                .productId("test-product-id")
                .productName("test-product-name")
                .imgUrl("12345".repeat(41))
                .productUrl("productUrl")
                .build();

            // when
            entity.updateFieldSize();

            // then
            assert entity.getImgUrl().length() == 100;
        }

        @Test
        @DisplayName("[success] productUrl 사이즈가 100을 초과할 때 사이즈가 수정되는지 확인한다.")
        void success4() {
            // given
            CrawlingProductEntity entity = CrawlingProductEntity.builder()
                .productId("test-product-id")
                .productName("test-product-name")
                .imgUrl("test-img-url")
                .productUrl("12345".repeat(41))
                .build();

            // when
            entity.updateFieldSize();

            // then
            assert entity.getProductUrl().length() == 100;
        }
    }
}