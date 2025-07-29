package com.odtrend.adapter.out.persistence.recommend;

import static com.odtrend.infrastructure.util.SnowflakeGenerator.isShard1;
import static com.odtrend.infrastructure.util.SnowflakeGenerator.nextId;

import com.odtrend.domain.model.ProductRecommend;
import com.odtrend.fakeClass.FakeRecommendShard1Adapter;
import com.odtrend.fakeClass.FakeRecommendShard2Adapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RecommendStorageAdapterTest {

    RecommendStorageAdapter adapter;
    FakeRecommendShard1Adapter shard1Adapter;
    FakeRecommendShard2Adapter shard2Adapter;

    RecommendStorageAdapterTest() {
        this.shard1Adapter = new FakeRecommendShard1Adapter();
        this.shard2Adapter = new FakeRecommendShard2Adapter();
        this.adapter = new RecommendStorageAdapter(shard1Adapter, shard2Adapter);
    }

    @AfterEach
    void tearDown() {
        shard1Adapter.database.clear();
        shard2Adapter.database.clear();
    }

    @Nested
    @DisplayName("[existsByCheckDate] 등록된 추천상품인지 확인하는 메소드")
    class Describe_existsByCheckDate {

        @Test
        @DisplayName("[success] 등록된 추천 상품이라면 true 를 응답한다.")
        void success1() {
            // given
            LocalDate checkDate = LocalDate.now().minusDays(0);
            List<ProductRecommend> recommends = List.of(ProductRecommend.builder()
                .productId(1L)
                .type("TREND")
                .checkDate(checkDate)
                .regDate(LocalDate.now())
                .regDateTime(LocalDateTime.now())
                .build());
            shard1Adapter.register(recommends);

            // when
            boolean result = adapter.existsByCheckDate(checkDate);

            // then
            assert result;
        }

        @Test
        @DisplayName("[success] 등록된 추천 상품이 아니라면 false 를 응답한다.")
        void success2() {
            LocalDate checkDate = LocalDate.now().minusDays(0);

            // when
            boolean result = adapter.existsByCheckDate(checkDate);

            // then
            assert !result;
        }
    }

    @Nested
    @DisplayName("[register] 추천상품을 등록하는 메소드")
    class Describe_register {

        @Test
        @DisplayName("[success] 상품 아이디를 shard key 로 하여 추천 상품 정보를 등록한다.")
        void success() {
            // given
            LocalDate checkDate1 = LocalDate.now().minusDays(0);
            LocalDate checkDate2 = LocalDate.now().minusDays(0);
            Long shard1Id = getId(true);
            Long shard2Id = getId(false);

            List<ProductRecommend> recommends = List.of(
                ProductRecommend.builder()
                    .productId(shard1Id)
                    .type("TREND")
                    .checkDate(checkDate1)
                    .regDate(LocalDate.now())
                    .regDateTime(LocalDateTime.now())
                    .build(),
                ProductRecommend.builder()
                    .productId(shard2Id)
                    .type("TREND")
                    .checkDate(checkDate2)
                    .regDate(LocalDate.now())
                    .regDateTime(LocalDateTime.now())
                    .build()
            );

            // when
            adapter.register(recommends);

            // then
            assert shard1Adapter.existsByCheckDate(checkDate1);
            assert shard2Adapter.existsByCheckDate(checkDate2);
        }
    }

    Long getId(boolean isShard1) {
        long id = nextId();
        if ((isShard1 && isShard1(id)) || (!isShard1 && !isShard1(id))) {
            return id;
        }
        return getId(isShard1);
    }
}