package com.odtrend.adapter.out.client.elasticsearch;

import lombok.Builder;

@Builder
record FindProductEsByEmbeddingRequest(
    int size,
    Knn knn

) {

    static FindProductEsByEmbeddingRequest of(
        float[] embedding
    ) {
        return FindProductEsByEmbeddingRequest.builder()
            .size(50)
            .knn(Knn.builder()
                .field("embedding")
                .query_vector(embedding)
                .k(50)
                .num_candidates(100)
                .build())
            .build();
    }

    @Builder
    record Knn(
        String field,
        float[] query_vector,
        int k,
        int num_candidates
    ) {

    }
}
