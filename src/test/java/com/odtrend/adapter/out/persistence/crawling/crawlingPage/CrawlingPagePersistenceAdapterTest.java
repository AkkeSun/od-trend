package com.odtrend.adapter.out.persistence.crawling.crawlingPage;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingPage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CrawlingPagePersistenceAdapterTest extends IntegrationTestSupport {

    @Autowired
    CrawlingPagePersistenceAdapter adapter;
    @Autowired
    CrawlingPageRepository crawlingPageRepository;

    @AfterEach
    void tearDown() {
        crawlingPageRepository.deleteAll();
    }

    @Nested
    @DisplayName("[findAll] 활성화된 크롤링 페이지를 조회하는 메소드")
    class Describe_findAll {

        @Test
        @DisplayName("[success] 등록된 정보가 있다면 크롤링 페이지를 응답한다.")
        void success() {
            // given
            CrawlingPageEntity entity1 = CrawlingPageEntity.builder()
                .shopCode("15")
                .url("test-url")
                .method("GET")
                .body("test-body")
                .category(Category.DIGITAL)
                .useYn("Y")
                .build();
            CrawlingPageEntity entity2 = CrawlingPageEntity.builder()
                .shopCode("20")
                .url("test-url2")
                .method("POST")
                .body("test-body2")
                .category(Category.FASHION)
                .useYn("N")
                .build();
            CrawlingPageEntity entity3 = CrawlingPageEntity.builder()
                .shopCode("25")
                .url("test-url3")
                .method("PUT")
                .body("test-body3")
                .category(Category.DIGITAL)
                .useYn("Y")
                .build();
            crawlingPageRepository.save(entity1);
            crawlingPageRepository.save(entity2);
            crawlingPageRepository.save(entity3);

            // when
            List<CrawlingPage> result = adapter.findAll();

            // then
            assert result.size() == 2;
            assert result.get(0).shopCode().equals(entity1.getShopCode());
            assert result.get(0).url().equals(entity1.getUrl());
            assert result.get(0).method().equals(entity1.getMethod());
            assert result.get(0).body().equals(entity1.getBody());
            assert result.get(0).category().equals(entity1.getCategory());
            assert result.get(0).useYn().equals(entity1.getUseYn());
            assert result.get(1).shopCode().equals(entity3.getShopCode());
            assert result.get(1).url().equals(entity3.getUrl());
            assert result.get(1).method().equals(entity3.getMethod());
            assert result.get(1).body().equals(entity3.getBody());
            assert result.get(1).category().equals(entity3.getCategory());
            assert result.get(1).useYn().equals(entity3.getUseYn());
        }

        @Test
        @DisplayName("[success] 등록된 정보가 없다면 빈 리스트를 응답한다.")
        void empty() {
            // when
            List<CrawlingPage> result = adapter.findAll();

            // then
            assert result.isEmpty();
        }
    }
}