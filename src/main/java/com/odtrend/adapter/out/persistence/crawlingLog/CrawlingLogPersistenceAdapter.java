package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.applicaiton.port.out.CrawlingLogStoragePort;
import com.odtrend.domain.model.CrawlingLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CrawlingLogPersistenceAdapter implements CrawlingLogStoragePort {

    private final CrawlingLogMapper crawlingLogMapper;

    private final CrawlingLogRepository crawlingLogRepository;

    @Override
    public void save(CrawlingLog crawlingLog) {
        CrawlingLogEntity crawlingLogEntity = crawlingLogMapper.toEntity(crawlingLog);
        crawlingLogRepository.save(crawlingLogEntity);
    }
}
