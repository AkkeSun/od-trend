package com.odtrend.adapter.out.persistence.recommend.shard1;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

interface RecommendShard1Repository extends JpaRepository<RecommendShard1Entity, Long> {

    boolean existsByCheckDateAndType(LocalDate checkDate, String type);
}
