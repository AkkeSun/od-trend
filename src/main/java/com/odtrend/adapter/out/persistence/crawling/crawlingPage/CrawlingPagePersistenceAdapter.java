package com.odtrend.adapter.out.persistence.crawling.crawlingPage;

import com.odtrend.applicaiton.port.out.CrawlingPageStoragePort;
import com.odtrend.domain.model.CrawlingPage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(transactionManager = "crawlingTransactionManager")
class CrawlingPagePersistenceAdapter implements CrawlingPageStoragePort {

    private final CrawlingPageRepository repository;

    @Override
    public List<CrawlingPage> findAll() {
        return repository.findAllByUseYn("Y").stream()
            .map(CrawlingPageEntity::toDomain)
            .toList();
    }
}
