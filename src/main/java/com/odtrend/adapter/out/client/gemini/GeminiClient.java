package com.odtrend.adapter.out.client.gemini;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface GeminiClient {

    @PostExchange("/v1/models/text-embedding-004:embedContent")
    GeminiEmbeddingResponse embedding(@RequestBody GeminiEmbeddingRequest request);
}
