package com.odtrend.applicaiton.port.out;

import java.util.List;

public interface ElasticSearchClientPort {

    List<Long> findIdByEmbedding(float[] embedding);
}
