package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.domain.model.CrawlingLog;
import org.springframework.stereotype.Component;

@Component
class CrawlingLogMapper {

    CrawlingLogEntity toEntity(CrawlingLog crawlingLog) {
        return CrawlingLogEntity.builder()
            .id(crawlingLog.id())
            .crawlingPageId(crawlingLog.crawlingPageId())
            .transactionId(crawlingLog.transactionId())
            .crawlingData(crawlingLog.crawlingData())
            .regDateTime(crawlingLog.regDateTime())
            .build();
    }
}
