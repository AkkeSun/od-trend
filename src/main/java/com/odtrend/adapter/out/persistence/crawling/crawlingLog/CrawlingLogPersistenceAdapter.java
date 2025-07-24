package com.odtrend.adapter.out.persistence.crawling.crawlingLog;

import com.odtrend.applicaiton.port.out.CrawlingLogStoragePort;
import com.odtrend.domain.model.CrawlingLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(transactionManager = "crawlingTransactionManager")
class CrawlingLogPersistenceAdapter implements CrawlingLogStoragePort {

    private final CrawlingLogRepository crawlingLogRepository;

    @Override
    public void save(CrawlingLog crawlingLog) {
        CrawlingLogEntity crawlingLogEntity = CrawlingLogEntity.of(crawlingLog);
        crawlingLogRepository.save(crawlingLogEntity);
    }
}
