package com.odtrend.applicaiton.service.register_product;

import static com.odtrend.infrastructure.util.GzipUtil.compress;

import com.odtrend.applicaiton.port.out.CrawlingLogStoragePort;
import com.odtrend.applicaiton.port.out.CrawlingProductStoragePort;
import com.odtrend.applicaiton.port.out.ErrorLogStoragePort;
import com.odtrend.applicaiton.port.out.ShopClientPort;
import com.odtrend.domain.model.CrawlingLog;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.domain.model.ErrorLog;
import com.odtrend.domain.service.ProductNormaizerFactory;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Slf4j
@Component
@RequiredArgsConstructor
class RegisterProductAsyncWorker implements RegisterProductAsync {

    private final ShopClientPort shopClientPort;

    private final ProductNormaizerFactory factory;

    private final ErrorLogStoragePort errorLogStoragePort;

    private final CrawlingLogStoragePort crawlingLogStoragePort;

    private final CrawlingProductStoragePort crawlingProductStoragePort;

    @Override
    public void registerProduct(CrawlingPage crawlingPage) {
        String transactionId = UUID.randomUUID().toString();
        log.info("register_product start - {}", transactionId);
        try {
            String crawlingResult = shopClientPort.getProducts(crawlingPage);
            log.info("register_product crawling success ");
            crawlingLogStoragePort.save(CrawlingLog.builder()
                .transactionId(transactionId)
                .crawlingPageId(crawlingPage.id())
                .crawlingData(compress(crawlingResult))
                .regDateTime(LocalDateTime.now())
                .build());

            List<CrawlingProduct> products = factory.getNormalizer(crawlingPage.shopCode())
                .normalize(crawlingPage, transactionId, crawlingResult);

            products.removeIf(product -> crawlingProductStoragePort.existsByShopCodeAndProductId(
                product.getShopCode(), product.getProductId()));

            // TODO: Gemini keyword 추출
            for (CrawlingProduct product : products) {
                //
            }

            crawlingProductStoragePort.saveAll(products);

            log.info("register_product: {} - {} - {}",
                crawlingPage.shopCode(), crawlingPage.category(), products.size());
        } catch (CustomBusinessException e) {
            errorLogStoragePort.save(ErrorLog.of(e, "register_product", transactionId));
        } catch (Exception e) {
            errorLogStoragePort.save(ErrorLog.of(e, "register_product", transactionId));
        }
    }
}
