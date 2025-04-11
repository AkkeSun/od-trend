package com.odtrend.domain.model;

import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ErrorLog(
    Long id,
    int errorCode,
    String errorMessage,
    String description,
    String domain,
    LocalDateTime regDateTime
) {

    public static ErrorLog of(CustomBusinessException exception, String domain,
        String description) {
        return ErrorLog.builder()
            .errorCode(exception.getErrorCode().getCode())
            .errorMessage(exception.getErrorCode().getMessage())
            .domain(domain)
            .description(description)
            .regDateTime(LocalDateTime.now())
            .build();
    }

    public static ErrorLog of(Exception e, String domain, String description) {
        return ErrorLog.builder()
            .errorCode(9999)
            .errorMessage(e.getMessage().length() > 100 ?
                e.getMessage().substring(0, 100) : e.getMessage())
            .domain(domain)
            .description(description)
            .regDateTime(LocalDateTime.now())
            .build();
    }
}
