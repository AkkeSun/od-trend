package com.odtrend.adapter.out.client.elasticsearch;

import com.odtrend.adapter.out.client.elasticsearch.FindProductEsRequest.Knn.Filter;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

@Builder
record FindProductEsRequest(
    int size,
    Knn knn

) {

    static FindProductEsRequest of(
        float[] embedding, String category
    ) {
        return FindProductEsRequest.builder()
            .size(20)
            .knn(Knn.builder()
                .filter(Filter.of(category))
                .field("embedding")
                .query_vector(embedding)
                .k(20)
                .num_candidates(100)
                .build())
            .build();
    }

    @Builder
    record Knn(
        Filter filter,
        String field,
        float[] query_vector,
        int k,
        int num_candidates
    ) {

        @Builder
        record Filter(
            Map<String, String> term
        ) {

            static Filter of(String category) {
                Map<String, String> filterItem = new HashMap<>();
                filterItem.put("category", category);

                return Filter.builder()
                    .term(filterItem)
                    .build();
            }
        }
    }
}
