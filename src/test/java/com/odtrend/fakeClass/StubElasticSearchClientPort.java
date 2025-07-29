package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.ElasticSearchClientPort;
import java.util.List;

public class StubElasticSearchClientPort implements ElasticSearchClientPort {

    @Override
    public List<Long> findIdByEmbeddingAndCategory(float[] embedding, String category) {
        return List.of(10L);
    }
}
