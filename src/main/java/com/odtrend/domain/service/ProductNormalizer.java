package com.odtrend.domain.service;

import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import java.util.List;

public interface ProductNormalizer {

    String getShopCode();

    List<CrawlingProduct> normalize(CrawlingPage crawlingPage, String transactionId,
        String crawlingResult);
}
