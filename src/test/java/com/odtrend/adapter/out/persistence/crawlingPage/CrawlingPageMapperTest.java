package com.odtrend.adapter.out.persistence.crawlingPage;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingPageHeader;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrawlingPageMapperTest {

    @Nested
    @DisplayName("[success] 도메인을 엔티티로 변환하는 메소드")
    class Describe_toEntity {

        @Test
        @DisplayName("[success] 도메인이 엔티티로 잘 변환되는지 확인한다.")
        void success() {
            // given
            CrawlingPage domain = CrawlingPage.builder()
                .id(1L)
                .shopCode("15")
                .url("test-url")
                .method("GET")
                .body("test-body")
                .category(Category.DIGITAL)
                .headers(Collections.singletonList(
                    CrawlingPageHeader.builder()
                        .id(1L)
                        .headerKey("key")
                        .headerValue("value")
                        .build()
                ))
                .useYn("Y")
                .build();

            // when
            CrawlingPageEntity entity = new CrawlingPageMapper().toEntity(domain);

            // then
            assert entity.getId().equals(domain.id());
            assert entity.getShopCode().equals(domain.shopCode());
            assert entity.getUrl().equals(domain.url());
            assert entity.getMethod().equals(domain.method());
            assert entity.getBody().equals(domain.body());
            assert entity.getCategory().equals(domain.category());
            assert entity.getHeaders().get(0).getId().equals(domain.headers().get(0).id());
            assert entity.getHeaders().get(0).getHeaderKey()
                .equals(domain.headers().get(0).headerKey());
            assert entity.getHeaders().get(0).getHeaderValue()
                .equals(domain.headers().get(0).headerValue());
            assert entity.getUseYn().equals(domain.useYn());
        }
    }
}