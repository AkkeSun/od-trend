package com.odtrend.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
public class CrawlingProduct {

    private Long id;
    private String transactionId;
    private String shopCode;
    private Category category;
    private String productId;
    private String productName;
    private String imgUrl;
    private String productUrl;
    private int price;
    private String keyword;
    private LocalDateTime regDateTime;

    @Builder
    public CrawlingProduct(Long id, String transactionId, String shopCode, Category category,
        String productId, String productName, String imgUrl, String productUrl, int price,
        String keyword, LocalDateTime regDateTime) {
        this.id = id;
        this.transactionId = transactionId;
        this.shopCode = shopCode;
        this.category = category;
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.price = price;
        this.keyword = keyword;
        this.regDateTime = regDateTime;
    }

    public boolean isValid() {
        return StringUtils.hasText(productId) && StringUtils.hasText(productName) &&
            StringUtils.hasText(imgUrl) && StringUtils.hasText(productUrl) && price > 0;
    }
}
