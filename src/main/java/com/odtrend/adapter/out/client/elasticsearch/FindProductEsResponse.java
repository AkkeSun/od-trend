package com.odtrend.adapter.out.client.elasticsearch;

import java.util.List;
import lombok.Builder;

@Builder
public record FindProductEsResponse(
    int took,
    boolean timed_out,
    Shards _shards,
    HitsWrapper hits
) {

    boolean isEmpty() {
        return hits.total.value == 0;
    }

    @Builder
    public record Shards(
        int total,
        int successful,
        int skipped,
        int failed
    ) {

    }

    @Builder
    public record HitsWrapper(
        Total total,
        Double max_score,
        List<Hit> hits
    ) {

    }

    @Builder
    public record Total(
        int value,
        String relation
    ) {

    }

    @Builder
    public record Hit(
        String _index,
        String _id,
        Double _score,
        DocumentSource _source
    ) {

        @Builder
        public record DocumentSource(
            String _class,
            Long productId,
            String productName,
            String keywords,
            String sellerEmail,
            String productImgUrl,
            long price,
            long salesCount,
            long reviewCount,
            double totalScore,
            String category,
            String regDateTime,
            float[] embedding
        ) {

        }
    }

}
