package com.odtrend.fakeClass;

import com.odtrend.adapter.out.persistence.recommend.shard1.RecommendShard1Adapter;
import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeRecommendShard1Adapter extends RecommendShard1Adapter {

    public List<ProductRecommend> database;

    public FakeRecommendShard1Adapter() {
        super(null);
        database = new ArrayList<>();
    }

    @Override
    public boolean existsByCheckDate(LocalDate checkDate) {
        return !database.stream()
            .filter(data -> data.checkDate().equals(checkDate))
            .toList().isEmpty();
    }

    @Override
    public void register(List<ProductRecommend> recommends) {
        database.addAll(recommends);
    }
}
