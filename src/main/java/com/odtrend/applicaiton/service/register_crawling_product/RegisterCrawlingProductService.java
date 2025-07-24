package com.odtrend.applicaiton.service.register_crawling_product;

import com.odtrend.applicaiton.port.in.RegisterCrawlingProductAsyncUseCase;
import com.odtrend.applicaiton.port.in.RegisterCrawlingProductUseCase;
import com.odtrend.applicaiton.port.out.CrawlingPageStoragePort;
import com.odtrend.domain.model.CrawlingPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterCrawlingProductService implements RegisterCrawlingProductUseCase {

    private final CrawlingPageStoragePort crawlingPageStoragePort;
    private final RegisterCrawlingProductAsyncUseCase asyncUseCase;

    @Override
    public void registerProduct() {
        for (CrawlingPage crawlingPage : crawlingPageStoragePort.findAll()) {
            asyncUseCase.registerProduct(crawlingPage);
        }
    }
}