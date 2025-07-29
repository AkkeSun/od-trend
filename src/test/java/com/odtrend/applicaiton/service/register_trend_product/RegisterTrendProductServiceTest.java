package com.odtrend.applicaiton.service.register_trend_product;

import static com.odtrend.domain.model.Category.DIGITAL;

import com.odtrend.applicaiton.port.in.RegisterTrendProductUseCase;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.domain.model.ProductRecommend;
import com.odtrend.fakeClass.FakeCrawlingProductStoragePort;
import com.odtrend.fakeClass.FakeRecommendStoragePort;
import com.odtrend.fakeClass.StubElasticSearchClientPort;
import com.odtrend.fakeClass.StubGeminiClientPort;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class RegisterTrendProductServiceTest {
    private StubGeminiClientPort geminiClientPort;
    private FakeRecommendStoragePort recommendStoragePort;
    private StubElasticSearchClientPort elasticSearchClientPort;
    private FakeCrawlingProductStoragePort crawlingProductStoragePort;
    private RegisterTrendProductUseCase useCase;

    RegisterTrendProductServiceTest(){
        this.geminiClientPort = new StubGeminiClientPort();
        this.recommendStoragePort = new FakeRecommendStoragePort();
        this.elasticSearchClientPort = new StubElasticSearchClientPort();
        this.crawlingProductStoragePort = new FakeCrawlingProductStoragePort();
        this.useCase = new RegisterTrendProductService(
            geminiClientPort, recommendStoragePort, elasticSearchClientPort,
            crawlingProductStoragePort
        );
    }

    @AfterEach
    void tearDown() {
        recommendStoragePort.database.clear();
        crawlingProductStoragePort.database.clear();
    }

    @Nested
    @DisplayName("[register] 추천 상품을 등록하는 메소드")
    class Describe_register {

        @Test
        @DisplayName("[success] 해당 날짜 기준으로 등록된 추천 상품이 있다면 서비스를 종료한다.")
        void success(CapturedOutput output) {
            // given
            LocalDate targetDate = LocalDate.now().with(DayOfWeek.MONDAY);
            recommendStoragePort.register(List.of(
                ProductRecommend.builder()
                    .productId(1L)
                    .checkDate(targetDate)
                    .type("TREND")
                    .regDate(LocalDate.now())
                    .regDateTime(LocalDateTime.now())
                    .build()
            ));

            // when
            useCase.register();

            // when
            assert !output.toString().contains("registerRecommendProduct");

        }

        @Test
        @DisplayName("[success] 카테고리와 등록 시간으로 조회된 크롤링 상품이 없으면 해당 카테고리 처리를 종료한다.")
        void success2(CapturedOutput output) {
            // when
            useCase.register();

            // then
            assert !output.toString().contains("registerRecommendProduct");
        }

        @Test
        @DisplayName("[success] 임베딩에 실패하면 서비스를 종료한다.")
        void success3(CapturedOutput output) {
            // given
            LocalDate targetDate = LocalDate.now().with(DayOfWeek.MONDAY);
            CrawlingProduct product = CrawlingProduct.builder()
                .id(10L)
                .shopCode("15")
                .category(DIGITAL)
                .productName("오류 상품")
                .price(10000)
                .regDateTime(targetDate.minusDays( 5).atStartOfDay())
                .build();
            crawlingProductStoragePort.database.add(product);

            // when
            useCase.register();

            // then
            assert recommendStoragePort.database.isEmpty();
            assert output.toString().contains("[registerProduct] embedding retry");

        }

        @Test
        @DisplayName("[success] 상품을 정상적으로 등록한다.")
        void success4() {
            // given
            LocalDate targetDate = LocalDate.now().with(DayOfWeek.MONDAY);
            CrawlingProduct product = CrawlingProduct.builder()
                .id(10L)
                .shopCode("15")
                .category(DIGITAL)
                .productName("product")
                .price(10000)
                .regDateTime(targetDate.minusDays( 5).atStartOfDay())
                .build();
            crawlingProductStoragePort.database.add(product);

            // when
            useCase.register();

            // then
            assert recommendStoragePort.database.getFirst().productId().equals(10L);
        }
    }
}