package com.odtrend.adapter.out.client.gemini;

import lombok.Builder;

@Builder
public record GeminiEmbeddingRequest(
    String model,
    GeminiContentRequest content,
    String taskType
) {

    static GeminiEmbeddingRequest of(String content) {
        return GeminiEmbeddingRequest.builder()
            .model("models/text-embedding-004")
            .content(GeminiContentRequest.of(content))
            .taskType("SEMANTIC_SIMILARITY")
            .build();
    }
}
