package com.odtrend.adapter.out.client.gemini;

import static com.odtrend.infrastructure.util.JsonUtil.toJsonString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.adapter.out.client.gemini.GeminiEmbeddingResponse.GeminiEmbedding;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import com.odtrend.infrastructure.exception.ErrorCode;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

class GeminiClientAdapterTest extends IntegrationTestSupport {

    @Autowired
    private GeminiClientAdapter geminiClientAdapter;
    static MockWebServer geminiServer;


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) throws IOException {
        geminiServer = new MockWebServer();
        geminiServer.start(3521);

        registry.add("service-constant.external.gemini.host",
            () -> geminiServer.url("/").toString());
        registry.add("service-constant.external.gemini.token", () -> "fake-token");
    }

    @AfterAll
    static void shutdown() throws IOException {
        geminiServer.shutdown();
    }

    @Nested
    @DisplayName("[embedding] 문자열을 임베딩하는 메소드")
    class Describe_embedding {

        @Test
        @DisplayName("[success] gemini API 를 통해 문자열을 임베딩한다.")
        void success() {
            // given
            String document = "test";
            String responseBody = toJsonString(GeminiEmbeddingResponse.builder()
                .embedding(GeminiEmbedding.builder()
                    .values(new float[]{123f, 456f})
                    .build())
                .build());
            geminiServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-type", "application/json")
                .setBody(responseBody));

            // when
            float[] result = geminiClientAdapter.embedding(document);

            // then
            assert result.length == 2;
            assert result[0] == 123;
            assert result[1] == 456;
        }

        @Test
        @DisplayName("[error] API 호출에 실패하면 예외를 응답한다.")
        void error() {
            // given
            String document = "test";
            geminiServer.enqueue(new MockResponse()
                .setResponseCode(500));

            // when
            CustomBusinessException result = assertThrows(CustomBusinessException.class, () -> {
                geminiClientAdapter.embedding(document);
            });

            // then
            assert result.getErrorCode().equals(ErrorCode.Client_Call_Error);
        }
    }
}