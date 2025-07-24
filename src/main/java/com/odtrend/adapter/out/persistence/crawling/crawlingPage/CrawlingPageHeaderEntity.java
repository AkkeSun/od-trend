package com.odtrend.adapter.out.persistence.crawling.crawlingPage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "CRAWLING_PAGE_HEADER")
public class CrawlingPageHeaderEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HEADER_KEY")
    private String headerKey;

    @Column(name = "HEADER_VALUE")
    private String headerValue;

    @Builder
    public CrawlingPageHeaderEntity(Long id, String headerKey, String headerValue) {
        this.id = id;
        this.headerKey = headerKey;
        this.headerValue = headerValue;
    }
}
