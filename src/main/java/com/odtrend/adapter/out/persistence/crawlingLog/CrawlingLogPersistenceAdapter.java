package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.applicaiton.port.out.CrawlingLogStoragePort;
import com.odtrend.domain.model.CrawlingLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CrawlingLogPersistenceAdapter implements CrawlingLogStoragePort {

    private final CrawlingLogRepository crawlingLogRepository;

    @Override
    public void save(CrawlingLog crawlingLog) {
        CrawlingLogEntity crawlingLogEntity = CrawlingLogEntity.of(crawlingLog);
        crawlingLogRepository.save(crawlingLogEntity);
    }
}
