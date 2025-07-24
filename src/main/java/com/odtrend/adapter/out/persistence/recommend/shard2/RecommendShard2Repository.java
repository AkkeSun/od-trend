package com.odtrend.adapter.out.persistence.recommend.shard2;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

interface RecommendShard2Repository extends JpaRepository<RecommendShard2Entity, Long> {

    boolean existsByCheckDateAndType(LocalDate checkDate, String type);
}
