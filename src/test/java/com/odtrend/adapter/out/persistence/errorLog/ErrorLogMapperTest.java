package com.odtrend.adapter.out.persistence.errorLog;

import com.odtrend.domain.model.ErrorLog;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ErrorLogMapperTest {

    @Nested
    @DisplayName("[toEntity] 도메인을 엔티티로 변환하는 메소드")
    class Describe_toEntity {

        @Test
        @DisplayName("[success] 도메인이 엔티티로 잘 변환되는지 확인한다.")
        void success() {
            // given
            ErrorLog domain = ErrorLog.builder()
                .id(1L)
                .errorCode(100)
                .errorMessage("test")
                .description("description")
                .domain("domain")
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            ErrorLogEntity entity = new ErrorLogMapper().toEntity(domain);

            // then
            assert entity.getId().equals(domain.id());
            assert entity.getErrorCode() == domain.errorCode();
            assert entity.getErrorMessage().equals(domain.errorMessage());
            assert entity.getDescription().equals(domain.description());
            assert entity.getDomain().equals(domain.domain());
            assert entity.getRegDateTime().equals(domain.regDateTime());
        }
    }

    @Nested
    @DisplayName("[toDomain] 엔티티를 도메인으로 변환하는 메소드")
    class Describe_toDomain {

        @Test
        @DisplayName("[success] 엔티티가 도메인으로 잘 변환되는지 확인한다.")
        void success() {
            // given
            ErrorLogEntity entity = ErrorLogEntity.builder()
                .id(1L)
                .errorCode(100)
                .errorMessage("test")
                .description("description")
                .domain("domain")
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            ErrorLog domain = new ErrorLogMapper().toDomain(entity);

            // then
            assert domain.id().equals(entity.getId());
            assert domain.errorCode() == entity.getErrorCode();
            assert domain.errorMessage().equals(entity.getErrorMessage());
            assert domain.description().equals(entity.getDescription());
            assert domain.domain().equals(entity.getDomain());
            assert domain.regDateTime().equals(entity.getRegDateTime());
        }
    }
}