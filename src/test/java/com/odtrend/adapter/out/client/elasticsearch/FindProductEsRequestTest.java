package com.odtrend.adapter.out.client.elasticsearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FindProductEsRequestTest {

    @Nested
    @DisplayName("[of] 임베딩 데이터와 카테고리로 요청 객채를 셍성하는 메소드")
    class Describe_of {

        @Test
        @DisplayName("[success] 임베딩 데이터와 카테고리로 요청 객채를 잘 생성하는지 확인한다")
        void success() {
            // given
            float[] embedding = new float[1];
            String category = "TEST";

            // when
            FindProductEsRequest result = FindProductEsRequest.of(embedding, category);

            // then
            assert result.size() == 20;
            assert result.knn().filter().term().get("category").equals(category);
            assert result.knn().field().equals("embedding");
            assert result.knn().query_vector().length == 1;
            assert result.knn().k() == 20;
            assert result.knn().num_candidates() == 100;
        }
    }
}