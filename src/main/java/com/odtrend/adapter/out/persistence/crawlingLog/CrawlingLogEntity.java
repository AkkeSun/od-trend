package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.domain.model.CrawlingLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "CRAWLING_LOG")
public class CrawlingLogEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CRAWLING_PAGE_ID")
    private Long crawlingPageId;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "CRAWLING_DATA", columnDefinition = "longtext")
    private String crawlingData;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    public CrawlingLogEntity(Long id, Long crawlingPageId, String transactionId,
        String crawlingData, LocalDateTime regDateTime) {
        this.id = id;
        this.crawlingPageId = crawlingPageId;
        this.transactionId = transactionId;
        this.crawlingData = crawlingData;
        this.regDateTime = regDateTime;
    }

    static CrawlingLogEntity of(CrawlingLog crawlingLog) {
        return CrawlingLogEntity.builder()
                .id(crawlingLog.id())
                .crawlingPageId(crawlingLog.crawlingPageId())
                .transactionId(crawlingLog.transactionId())
                .crawlingData(crawlingLog.crawlingData())
                .regDateTime(crawlingLog.regDateTime())
                .build();
    }
}
