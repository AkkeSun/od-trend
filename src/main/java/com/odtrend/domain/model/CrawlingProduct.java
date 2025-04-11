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
    private String description;
    private LocalDateTime regDateTime;

    @Builder
    public CrawlingProduct(Long id, String transactionId, String shopCode, Category category,
        String productId, String productName, String imgUrl, String productUrl, int price,
        String description, LocalDateTime regDateTime) {
        this.id = id;
        this.transactionId = transactionId;
        this.shopCode = shopCode;
        this.category = category;
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.price = price;
        this.description = description;
        this.regDateTime = regDateTime;
    }

    public void updateDescription(String keyword) {
        this.description = description;
    }

    public boolean isValid() {
        return StringUtils.hasText(productId) && StringUtils.hasText(productName) &&
            StringUtils.hasText(imgUrl) && StringUtils.hasText(productUrl) && price > 0;
    }
}
