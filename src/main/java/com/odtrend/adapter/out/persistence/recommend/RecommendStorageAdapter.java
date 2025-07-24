package com.odtrend.adapter.out.persistence.recommend;

import static com.odtrend.infrastructure.util.SnowflakeGenerator.isShard1;

import com.odtrend.adapter.out.persistence.recommend.shard1.RecommendShard1Adapter;
import com.odtrend.adapter.out.persistence.recommend.shard2.RecommendShard2Adapter;
import com.odtrend.applicaiton.port.out.RecommendStoragePort;
import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class RecommendStorageAdapter implements RecommendStoragePort {

    private final RecommendShard1Adapter shard1Adapter;
    private final RecommendShard2Adapter shard2Adapter;

    @Override
    public boolean existsByCheckDate(LocalDate checkDate) {
        if (shard1Adapter.existsByCheckDate(checkDate)) {
            return true;
        }
        return shard2Adapter.existsByCheckDate(checkDate);
    }

    @Override
    public void register(List<ProductRecommend> recommends) {
        List<ProductRecommend> shard1List = new ArrayList<>();
        List<ProductRecommend> shard2List = new ArrayList<>();
        for (ProductRecommend recommend : recommends) {
            if (isShard1(recommend.productId())) {
                shard1List.add(recommend);
            } else {
                shard2List.add(recommend);
            }
        }

        shard1Adapter.register(shard1List);
        shard2Adapter.register(shard2List);
    }
}
