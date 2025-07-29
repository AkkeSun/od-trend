package com.odtrend.adapter.out.persistence.recommend.shard1;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RecommendShard1AdapterTest extends IntegrationTestSupport {

    @Autowired
    RecommendShard1Adapter adapter;

    @Autowired
    RecommendShard1Repository repository;

    @AfterEach
    void reset() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("[existsByCheckDate] 해당 날짜에 등록된 상품이 있는지 확인하는 메소드")
    class Describe_existsByCheckDate {

        @Test
        @DisplayName("[success] 해당 날짜에 등록된 상품이 있으면 true 를 응답한다.")
        void success() {
            // given
            LocalDate checkDate = LocalDate.now();
            RecommendShard1Entity entity = RecommendShard1Entity.builder()
                .id(1L)
                .productId(1L)
                .type("TREND")
                .checkDate(checkDate)
                .regDate(LocalDate.now())
                .regDateTime(LocalDateTime.now())
                .build();
            repository.save(entity);

            // when
            boolean result = adapter.existsByCheckDate(checkDate);

            // then
            assert result;
        }

        @Test
        @DisplayName("[success] 해당 날짜에 등록된 상품이 없으면 false 를 응답한다.")
        void success2() {
            // given
            LocalDate checkDate = LocalDate.now().minusDays(1);

            // when
            boolean result = adapter.existsByCheckDate(checkDate);

            // then
            assert !result;
        }
    }

    @Nested
    @DisplayName("[register] 추천 상품을 등록하는 메소드")
    class Describe_register {

        @Test
        @DisplayName("[success] 추천상품이 잘 등록되는지 확인한다.")
        void success() {
            // given
            LocalDate checkDate = LocalDate.now().minusDays(0);
            List<ProductRecommend> recommends = List.of(ProductRecommend.builder()
                .productId(1L)
                .type("TREND")
                .checkDate(checkDate)
                .regDate(LocalDate.now())
                .regDateTime(LocalDateTime.now())
                .build());

            // when
            adapter.register(recommends);

            // then
            assert adapter.existsByCheckDate(checkDate);
        }
    }
}