package com.odtrend.adapter.out.client.elasticsearch;

import static com.odtrend.infrastructure.util.JsonUtil.toJsonString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.adapter.out.client.elasticsearch.FindProductEsResponse.Hit;
import com.odtrend.adapter.out.client.elasticsearch.FindProductEsResponse.Hit.DocumentSource;
import com.odtrend.adapter.out.client.elasticsearch.FindProductEsResponse.HitsWrapper;
import com.odtrend.adapter.out.client.elasticsearch.FindProductEsResponse.Total;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import com.odtrend.infrastructure.exception.ErrorCode;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

class ElasticSearchClientAdapterTest extends IntegrationTestSupport {

    @Autowired
    private ElasticSearchClientAdapter elasticSearchClientAdapter;

    static MockWebServer elasticsearchServer;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) throws IOException {
        elasticsearchServer = new MockWebServer();
        elasticsearchServer.start(9200);

        registry.add("service-constant.external.elasticsearch.host",
            () -> elasticsearchServer.url("/").toString());
    }

    @AfterAll
    static void shutdown() throws IOException {
        elasticsearchServer.shutdown();
    }

    @Nested
    @DisplayName("[findIdByEmbeddingAndCategory] 엘라스틱서치에 등록된 상품 아이디를 조회하는 메소드")
    class Describe_findIdByEmbeddingAndCategory {

        @Test
        @DisplayName("[error] 조회중 오류 발생시 CustomBusinessException 를 응답한다.")
        void error() {
            // given
            elasticsearchServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setHeader("Content-type", "application/json"));
            float[] embedding = new float[768];
            String category = "DIGITAL";

            // when
            CustomBusinessException result = assertThrows(CustomBusinessException.class, () -> {
                elasticSearchClientAdapter.findIdByEmbeddingAndCategory(embedding, category);
            });

            // then
            assert result.getErrorCode().equals(ErrorCode.Client_Call_Error);
        }

        @Test
        @DisplayName("[success] 조회된 상품 정보가 없으면 빈 리스트를 응답한다.")
        void success() {
            // given
            elasticsearchServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-type", "application/json")
                .setBody(toJsonString(FindProductEsResponse.builder()
                    .hits(HitsWrapper.builder()
                        .total(Total.builder()
                            .value(0)
                            .build())
                        .build())
                    .build())));
            float[] embedding = new float[768];
            String category = "DIGITAL";

            // when
            List<Long> result = elasticSearchClientAdapter.findIdByEmbeddingAndCategory(embedding,
                category);

            // then
            assert result.isEmpty();
        }

        @Test
        @DisplayName("[success] 조회된 상품 정보가 있다면 아이디 목록을 응답한다.")
        void success2() {
            // given
            elasticsearchServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-type", "application/json")
                .setBody(toJsonString(FindProductEsResponse.builder()
                    .hits(HitsWrapper.builder()
                        .total(Total.builder()
                            .value(1)
                            .build())
                        .hits(List.of(Hit.builder()
                            ._source(DocumentSource.builder()
                                .productId(10L)
                                .productName("테스트 상품")
                                .keywords("신발,운동화")
                                .sellerEmail("email")
                                .productImgUrl("http://example.com/image.jpg")
                                .price(10000L)
                                .salesCount(100L)
                                .reviewCount(50L)
                                .totalScore(4.5)
                                .category("FASHION")
                                .regDateTime(LocalDateTime.of(2025, 1, 1, 0, 0, 0).format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .build())
                            .build()))
                        .build())
                    .build())));
            float[] embedding = new float[768];
            String category = "DIGITAL";

            // when
            List<Long> result = elasticSearchClientAdapter.findIdByEmbeddingAndCategory(embedding,
                category);

            // then
            assert result.size() == 1;
            assert result.getFirst() == 10L;
        }
    }
}