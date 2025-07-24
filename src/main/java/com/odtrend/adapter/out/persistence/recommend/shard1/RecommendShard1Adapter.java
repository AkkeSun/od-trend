package com.odtrend.adapter.out.persistence.recommend.shard1;

import com.odtrend.applicaiton.port.out.RecommendStoragePort;
import com.odtrend.domain.model.ProductRecommend;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(transactionManager = "recommendShard1TransactionManager")
public class RecommendShard1Adapter implements RecommendStoragePort {

    private final RecommendShard1Repository repository;

    @Override
    public boolean existsByCheckDate(LocalDate checkDate) {
        return repository.existsByCheckDateAndType(checkDate, "TREND");
    }

    @Override
    public void register(List<ProductRecommend> recommends) {
        repository.saveAll(recommends.stream().map(RecommendShard1Entity::of).toList());
    }
}
