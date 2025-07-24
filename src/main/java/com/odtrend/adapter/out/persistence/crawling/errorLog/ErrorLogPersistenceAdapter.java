package com.odtrend.adapter.out.persistence.crawling.errorLog;

import com.odtrend.applicaiton.port.out.ErrorLogStoragePort;
import com.odtrend.domain.model.ErrorLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(transactionManager = "crawlingTransactionManager")
class ErrorLogPersistenceAdapter implements ErrorLogStoragePort {

    private final ErrorLogRepository errorLogRepository;

    @Override
    public void save(ErrorLog errorLog) {
        errorLogRepository.save(ErrorLogEntity.of(errorLog));
    }
}
