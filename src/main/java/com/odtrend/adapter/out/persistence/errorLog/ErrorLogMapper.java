package com.odtrend.adapter.out.persistence.errorLog;

import com.odtrend.domain.model.ErrorLog;
import org.springframework.stereotype.Component;

@Component
class ErrorLogMapper {

    public ErrorLogEntity toEntity(ErrorLog errorLog) {
        return ErrorLogEntity.builder()
            .id(errorLog.id())
            .errorMessage(errorLog.errorMessage())
            .errorCode(errorLog.errorCode())
            .domain(errorLog.domain())
            .description(errorLog.description())
            .regDateTime(errorLog.regDateTime())
            .build();
    }

    public ErrorLog toDomain(ErrorLogEntity errorLogEntity) {
        return ErrorLog.builder()
            .id(errorLogEntity.getId())
            .errorMessage(errorLogEntity.getErrorMessage())
            .errorCode(errorLogEntity.getErrorCode())
            .domain(errorLogEntity.getDomain())
            .description(errorLogEntity.getDescription())
            .regDateTime(errorLogEntity.getRegDateTime())
            .build();
    }
}
