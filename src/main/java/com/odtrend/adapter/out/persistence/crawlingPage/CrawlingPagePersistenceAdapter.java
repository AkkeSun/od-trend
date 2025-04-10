package com.odtrend.adapter.out.persistence.crawlingPage;

import com.odtrend.applicaiton.port.out.CrawlingPageStoragePort;
import com.odtrend.domain.model.CrawlingPage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CrawlingPagePersistenceAdapter implements CrawlingPageStoragePort {

    private final CrawlingPageMapper mapper;
    private final CrawlingPageRepository repository;

    @Override
    public List<CrawlingPage> findAll() {
        return repository.findAllByUseYn("Y").stream()
            .map(mapper::toDomain)
            .toList();
    }
}
