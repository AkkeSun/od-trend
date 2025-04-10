package com.odtrend.adapter.out.persistence.crawlingProduct;

import com.odtrend.domain.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "CRAWLING_PRODUCT")
public class CrawlingProductEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "SHOP_CODE")
    private String shopCode;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "IMG_URL")
    private String imgUrl;

    @Column(name = "PRODUCT_URL")
    private String productUrl;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    public CrawlingProductEntity(Long id, String transactionId, String shopCode, Category category,
        String productId, String productName, String imgUrl, String productUrl, Integer price,
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
}
