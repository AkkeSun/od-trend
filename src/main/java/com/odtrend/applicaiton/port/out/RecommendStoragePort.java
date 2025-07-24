package com.odtrend.applicaiton.port.out;

import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.util.List;

public interface RecommendStoragePort {

    boolean existsByCheckDate(LocalDate checkDate);

    void register(List<ProductRecommend> recommends);
}
