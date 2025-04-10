package com.odtrend.adapter.out.persistence.errorLog;

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
public class ErrorLogEntity {

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
    public ErrorLogEntity(Long id, String domain, int errorCode, String errorMessage,
        String description, LocalDateTime regDateTime) {
        this.id = id;
        this.domain = domain;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
        this.regDateTime = regDateTime;
    }
}
