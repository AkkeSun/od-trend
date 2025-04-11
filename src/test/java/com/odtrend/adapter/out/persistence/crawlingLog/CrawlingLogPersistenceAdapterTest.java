package com.odtrend.adapter.out.persistence.crawlingLog;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.CrawlingLog;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CrawlingLogPersistenceAdapterTest extends IntegrationTestSupport {

    @Autowired
    CrawlingLogPersistenceAdapter adapter;

    @Autowired
    CrawlingLogRepository crawlingLogRepository;

    @AfterEach
    void tearDown() {
        crawlingLogRepository.deleteAll();
    }

    @Nested
    @DisplayName("[saveAll] 크롤링 로그를 등록하는 메소드")
    class Describe_saveAll {

        @Test
        @DisplayName("[success] 크롤링 로그가 정상적으로 등록되는지 확인한다.")
        void success() {
            // given
            CrawlingLog log = CrawlingLog.builder()
                .crawlingPageId(10L)
                .transactionId("test")
                .crawlingData("crawlingData")
                .regDateTime(LocalDateTime.of(2025, 12, 12, 1, 2, 3))
                .build();

            // when
            adapter.save(log);

            // then
            CrawlingLogEntity entity = crawlingLogRepository.findAll().getFirst();
            assert entity.getCrawlingPageId().equals(log.crawlingPageId());
            assert entity.getTransactionId().equals(log.transactionId());
            assert entity.getCrawlingData().equals(log.crawlingData());
            assert entity.getRegDateTime().equals(log.regDateTime());
        }
    }
}