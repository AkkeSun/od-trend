package com.odtrend.domain.service;

import static com.odtrend.domain.model.ShopInfo.NAVER;
import static com.odtrend.infrastructure.util.JsonUtil.toJsonNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import com.odtrend.infrastructure.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NaverProductNormalizer implements ProductNormalizer {

    @Override
    public String getShopCode() {
        return NAVER.getShopCode();
    }

    @Override
    public List<CrawlingProduct> normalize(CrawlingPage crawlingPage, String transactionId,
        String crawlingResult) {
        try {
            List<CrawlingProduct> products = new ArrayList<>();

            JsonNode json = toJsonNode(crawlingResult);
            for (JsonNode productJson : json.get("products")) {
                CrawlingProduct product = CrawlingProduct.builder()
                    .transactionId(transactionId)
                    .shopCode(crawlingPage.shopCode())
                    .category(crawlingPage.category())
                    .productId(productJson.get("productId").asText())
                    .productName(productJson.get("title").asText())
                    .imgUrl(productJson.get("imageUrl").asText())
                    .productUrl(productJson.get("linkUrl").asText())
                    .price(productJson.get("priceValue").asInt())
                    .build();

                if (product.isValid()) {
                    products.add(product);
                }
            }

            if (products.isEmpty()) {
                throw new CustomBusinessException(ErrorCode.Product_Nomalizer_Error);
            }

            return products;
        } catch (Exception e) {
            log.error("NaverProductNormalizer error: {}", e.getMessage());
            throw new CustomBusinessException(ErrorCode.Product_Nomalizer_Error);
        }
    }
}
