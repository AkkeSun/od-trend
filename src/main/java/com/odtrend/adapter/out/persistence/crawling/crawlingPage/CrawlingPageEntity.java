package com.odtrend.adapter.out.persistence.crawling.crawlingPage;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingPageHeader;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "CRAWLING_PAGE")
class CrawlingPageEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SHOP_CODE")
    private String shopCode;

    @Column(name = "URL")
    private String url;

    @Column(name = "METHOD")
    private String method;

    @Column(name = "BODY")
    private String body;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "USE_YN")
    private String useYn;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "CRAWLING_PAGE_ID")
    private List<CrawlingPageHeaderEntity> headers;

    @Builder
    CrawlingPageEntity(Long id, String shopCode, String url, String method, String body,
        Category category, String useYn, List<CrawlingPageHeaderEntity> headers) {
        this.id = id;
        this.shopCode = shopCode;
        this.url = url;
        this.method = method;
        this.body = body;
        this.category = category;
        this.useYn = useYn;
        this.headers = headers;
    }

    static CrawlingPageEntity of(CrawlingPage crawlingPage) {
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

    CrawlingPage toDomain() {
        return CrawlingPage.builder()
            .id(id)
            .url(url)
            .body(body)
            .shopCode(shopCode)
            .method(method)
            .category(category)
            .headers(headers.isEmpty() ? null :
                headers.stream()
                    .map(persistence -> CrawlingPageHeader.builder()
                        .id(persistence.getId())
                        .headerKey(persistence.getHeaderKey())
                        .headerValue(persistence.getHeaderValue())
                        .build())
                    .toList())
            .useYn(useYn)
            .build();
    }
}