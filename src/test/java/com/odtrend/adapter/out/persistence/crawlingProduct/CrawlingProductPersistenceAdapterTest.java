package com.odtrend.adapter.out.persistence.crawlingProduct;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.CrawlingProduct;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CrawlingProductPersistenceAdapterTest extends IntegrationTestSupport {

    @Autowired
    CrawlingProductPersistenceAdapter adapter;
    @Autowired
    CrawlingProductRepository crawlingProductRepository;

    @AfterEach
    void tearDown() {
        crawlingProductRepository.deleteAll();
    }

    @Nested
    @DisplayName("[saveAll] 크롤링 상품을 등록하는 메소드")
    class Describe_saveAll {

        @Test
        @DisplayName("[success] 크롤링 상품이 정상적으로 등록되는지 확인한다.")
        void success() {
            // given
            CrawlingProduct product = CrawlingProduct.builder()
                .transactionId("transactionId")
                .shopCode("05")
                .productId("id")
                .productName("name")
                .price(1000)
                .imgUrl("imgUrl")
                .productUrl("productUrl")
                .description("description")
                .build();
            CrawlingProduct product2 = CrawlingProduct.builder()
                .transactionId("transactionId")
                .shopCode("05")
                .productId("id2")
                .productName("name2")
                .price(2000)
                .imgUrl("imgUrl2")
                .productUrl("productUrl2")
                .description("description2")
                .build();

            // when
            adapter.saveAll(List.of(product, product2));

            // then
            List<CrawlingProductEntity> entities = crawlingProductRepository.findAll();
            assert entities.size() == 2;
            assert entities.get(0).getTransactionId().equals(product.getTransactionId());
            assert entities.get(0).getShopCode().equals(product.getShopCode());
            assert entities.get(0).getProductId().equals(product.getProductId());
            assert entities.get(0).getProductName().equals(product.getProductName());
            assert entities.get(0).getPrice().equals(product.getPrice());
            assert entities.get(0).getImgUrl().equals(product.getImgUrl());
            assert entities.get(0).getProductUrl().equals(product.getProductUrl());
            assert entities.get(0).getDescription().equals(product.getDescription());
            assert entities.get(1).getTransactionId().equals(product2.getTransactionId());
            assert entities.get(1).getShopCode().equals(product2.getShopCode());
            assert entities.get(1).getProductId().equals(product2.getProductId());
            assert entities.get(1).getProductName().equals(product2.getProductName());
            assert entities.get(1).getPrice().equals(product2.getPrice());
            assert entities.get(1).getImgUrl().equals(product2.getImgUrl());
            assert entities.get(1).getProductUrl().equals(product2.getProductUrl());
            assert entities.get(1).getDescription().equals(product2.getDescription());

        }
    }
}