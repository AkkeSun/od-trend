package com.odtrend.adapter.out.persistence.crawling.errorLog;

import com.odtrend.domain.model.ErrorLog;
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
@Entity(name = "ERROR_LOG")
class ErrorLogEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DOMAIN")
    private String domain;

    @Column(name = "ERROR_CODE")
    private int errorCode;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    ErrorLogEntity(Long id, String domain, int errorCode, String errorMessage,
        String description, LocalDateTime regDateTime) {
        this.id = id;
        this.domain = domain;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
        this.regDateTime = regDateTime;
    }

    static ErrorLogEntity of(ErrorLog errorLog) {
        return ErrorLogEntity.builder()
            .id(errorLog.id())
            .errorMessage(errorLog.errorMessage())
            .errorCode(errorLog.errorCode())
            .domain(errorLog.domain())
            .description(errorLog.description())
            .regDateTime(errorLog.regDateTime())
            .build();
    }

    ErrorLog toDomain() {
        return ErrorLog.builder()
            .id(id)
            .errorMessage(errorMessage)
            .errorCode(errorCode)
            .domain(domain)
            .description(description)
            .regDateTime(regDateTime)
            .build();
    }
}
