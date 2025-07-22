package com.odtrend.adapter.out.persistence.errorLog;

import com.odtrend.applicaiton.port.out.ErrorLogStoragePort;
import com.odtrend.domain.model.ErrorLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ErrorLogPersistenceAdapter implements ErrorLogStoragePort {

    private final ErrorLogRepository errorLogRepository;

    @Override
    public void save(ErrorLog errorLog) {
        errorLogRepository.save(ErrorLogEntity.of(errorLog));
    }
}
