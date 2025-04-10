package com.odtrend.applicaiton.service.register_product;

import com.odtrend.applicaiton.port.in.RegisterProductUseCase;
import com.odtrend.applicaiton.port.out.CrawlingPageStoragePort;
import com.odtrend.domain.model.CrawlingPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterProductService implements RegisterProductUseCase {

    private final CrawlingPageStoragePort crawlingPageStoragePort;
    private final RegisterProductAsync registerProductAsync;

    @Override
    public void registerProduct() {
        for (CrawlingPage crawlingPage : crawlingPageStoragePort.findAll()) {
            registerProductAsync.registerProduct(crawlingPage);
        }
    }
}