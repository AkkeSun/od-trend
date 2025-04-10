package com.odtrend.adapter.out.persistence.crawlingPage;

import com.odtrend.domain.model.Category;
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
public class CrawlingPageEntity {

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
    public CrawlingPageEntity(Long id, String shopCode, String url, String method, String body,
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
}