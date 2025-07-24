package com.odtrend.adapter.out.persistence.crawling.crawlingPage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlingPageRepository extends JpaRepository<CrawlingPageEntity, Long> {

    List<CrawlingPageEntity> findAllByUseYn(String useYn);
}
