package com.odtrend.adapter.out.external;

import static org.junit.Assert.assertThrows;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingPageHeader;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import com.odtrend.infrastructure.exception.ErrorCode;
import java.io.IOException;
import java.util.Collections;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ShopClientAdapterTest extends IntegrationTestSupport {

    @Autowired
    private ShopClientAdapter adapter;
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8090);
    }

    @AfterEach
    public void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @Nested
    @DisplayName("[getProducts] 쇼핑몰의 인기 상품을 조회하는 클라이언트")
    class Describe_getProducts {

        @Test
        @DisplayName("[success] GET Method API 호출에 성공하는 경우 API 응답 바디를 리턴한다.")
        void success() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test").toString())
                .shopCode("15")
                .method("GET")
                .build();
            String responseBody = "success";
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

            // when
            String response = adapter.getProducts(crawlingPage);

            // then
            assert response.equals(responseBody);
        }

        @Test
        @DisplayName("[success] POST Method API 호출에 성공하는 경우 API 응답 바디를 리턴한다.")
        void success2() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test2").toString())
                .shopCode("15")
                .method("POST")
                .body("{'key':'val'}")
                .headers(Collections.singletonList(CrawlingPageHeader.builder()
                    .headerKey("contentType")
                    .headerValue("application/json")
                    .build()))
                .build();
            String responseBody = "success";
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

            // when
            String response = adapter.getProducts(crawlingPage);

            // then
            assert response.equals(responseBody);
        }

        @Test
        @DisplayName("[error] API 호출 응답 바디가 빈 문자열인 경우 예외를 응답한다.")
        void error() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test3").toString())
                .shopCode("15")
                .method("GET")
                .build();
            String responseBody = "";
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseBody));

            // when
            CustomBusinessException response = assertThrows(CustomBusinessException.class, () ->
                adapter.getProducts(crawlingPage));

            // then
            assert response.getErrorCode().equals(ErrorCode.Client_Call_Error);
        }

        @Test
        @DisplayName("[error] API 호출에 실패하는 경우 예외를 응답한다.")
        void error2() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test3").toString())
                .shopCode("15")
                .method("GET")
                .build();
            String responseBody = "success";
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody(responseBody));

            // when
            CustomBusinessException response = assertThrows(CustomBusinessException.class, () ->
                adapter.getProducts(crawlingPage));

            // then
            assert response.getErrorCode().equals(ErrorCode.Client_Call_Error);
        }
    }
}