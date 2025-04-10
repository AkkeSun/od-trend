package com.odtrend.adapter.out.persistence.crawlingPage;

import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingPageHeader;
import org.springframework.stereotype.Component;

@Component
class CrawlingPageMapper {

    public CrawlingPageEntity toEntity(CrawlingPage crawlingPage) {
        return CrawlingPageEntity.builder()
            .id(crawlingPage.id())
            .url(crawlingPage.url())
            .body(crawlingPage.body())
            .shopCode(crawlingPage.shopCode())
            .method(crawlingPage.method())
            .category(crawlingPage.category())
            .headers(crawlingPage.headers().isEmpty() ? null :
                crawlingPage.headers().stream()
                    .map(domain -> CrawlingPageHeaderEntity.builder()
                        .id(domain.id())
                        .headerKey(domain.headerKey())
                        .headerValue(domain.headerValue())
                        .build())
                    .toList())
            .useYn(crawlingPage.useYn())
            .build();
    }

    public CrawlingPage toDomain(CrawlingPageEntity entity) {
        return CrawlingPage.builder()
            .id(entity.getId())
            .url(entity.getUrl())
            .body(entity.getBody())
            .shopCode(entity.getShopCode())
            .method(entity.getMethod())
            .category(entity.getCategory())
            .headers(entity.getHeaders().isEmpty() ? null :
                entity.getHeaders().stream()
                    .map(persistence -> CrawlingPageHeader.builder()
                        .id(persistence.getId())
                        .headerKey(persistence.getHeaderKey())
                        .headerValue(persistence.getHeaderValue())
                        .build())
                    .toList())
            .build();
    }
}
