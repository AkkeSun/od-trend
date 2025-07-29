package com.odtrend.fakeClass;

import com.odtrend.applicaiton.port.out.RecommendStoragePort;
import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeRecommendStoragePort implements RecommendStoragePort {

    public List<ProductRecommend> database = new ArrayList<>();

    @Override
    public boolean existsByCheckDate(LocalDate checkDate) {
        return false;
    }

    @Override
    public void register(List<ProductRecommend> recommends) {
        database.addAll(recommends);
    }
}
