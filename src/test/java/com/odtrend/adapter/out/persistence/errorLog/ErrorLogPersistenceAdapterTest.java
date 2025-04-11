package com.odtrend.adapter.out.persistence.errorLog;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.ErrorLog;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ErrorLogPersistenceAdapterTest extends IntegrationTestSupport {

    @Autowired
    ErrorLogPersistenceAdapter errorLogPersistenceAdapter;
    @Autowired
    ErrorLogRepository errorLogRepository;

    @AfterEach
    void tearDown() {
        errorLogRepository.deleteAll();
    }

    @Nested
    @DisplayName("[save] 에러 로그를 저장하는 메소드")
    class Describe_save {

        @Test
        @DisplayName("[success] 에러 로그가 잘 저장되는지 확인한다.")
        void success() {
            // given
            ErrorLog errorLog = ErrorLog.builder()
                .errorCode(100)
                .errorMessage("test")
                .description("description")
                .domain("domain")
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            errorLogPersistenceAdapter.save(errorLog);

            // then
            errorLogRepository.findAll()
                .forEach(entity -> {
                    assert entity.getErrorCode() == errorLog.errorCode();
                    assert entity.getErrorMessage().equals(errorLog.errorMessage());
                    assert entity.getDescription().equals(errorLog.description());
                    assert entity.getDomain().equals(errorLog.domain());
                    assert entity.getRegDateTime().equals(errorLog.regDateTime());
                });
        }
    }
}