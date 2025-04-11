package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.domain.model.CrawlingLog;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrawlingLogMapperTest {

    @Nested
    @DisplayName("[success] 도메인을 엔티티로 변환하는 메소드")
    class Describe_toEntity {

        @Test
        @DisplayName("[success] 도메인이 엔티티로 잘 변환되는지 확인한다.")
        void success() {
            // given
            CrawlingLog domain = CrawlingLog.builder()
                .id(1L)
                .crawlingPageId(10L)
                .transactionId("test")
                .crawlingData("crawlingData")
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            CrawlingLogEntity entity = new CrawlingLogMapper().toEntity(domain);

            // then
            assert entity.getId().equals(domain.id());
            assert entity.getCrawlingPageId().equals(domain.crawlingPageId());
            assert entity.getTransactionId().equals(domain.transactionId());
            assert entity.getCrawlingData().equals(domain.crawlingData());
            assert entity.getRegDateTime().equals(domain.regDateTime());
        }
    }
}