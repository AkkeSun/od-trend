package com.odtrend.adapter.out.client.gemini;

import lombok.Builder;

@Builder
public record GeminiEmbeddingResponse(
    GeminiEmbedding embedding
) {

    @Builder
    record GeminiEmbedding(
        float[] values
    ) {

    }

    float[] getResponse() {
        return embedding.values;
    }
}
