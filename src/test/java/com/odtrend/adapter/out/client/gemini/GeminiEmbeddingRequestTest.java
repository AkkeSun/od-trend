package com.odtrend.adapter.out.client.gemini;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GeminiEmbeddingRequestTest {

    @Nested
    @DisplayName("[of] 입력값으로 GeminiEmbeddingRequest 를 생성하는 메소드")
    class Describe_of {

        @Test
        @DisplayName("[success] 입력값으로 GeminiEmbeddingRequest 를 잘 생성하는지 확인한다.")
        void success() {
            // given
            String content = "hello";

            // when
            GeminiEmbeddingRequest result = GeminiEmbeddingRequest.of(content);

            // then
            assert result.model().equals("models/text-embedding-004");
            assert result.content().parts().getFirst().text().equals(content);
            assert result.taskType().equals("SEMANTIC_SIMILARITY");
        }
    }
}