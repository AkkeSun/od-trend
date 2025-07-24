package com.odtrend.domain.service;

import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import java.util.List;

public interface ProductNormalizer {

    List<CrawlingProduct> normalize(CrawlingPage crawlingPage, String transactionId,
        String crawlingResult);
}
