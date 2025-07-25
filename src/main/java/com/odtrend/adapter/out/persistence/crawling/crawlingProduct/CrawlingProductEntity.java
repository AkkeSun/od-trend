package com.odtrend.adapter.out.persistence.crawling.crawlingProduct;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingProduct;
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
class CrawlingProductEntity {

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

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    CrawlingProductEntity(Long id, String transactionId, String shopCode, Category category,
        String productId, String productName, String imgUrl, String productUrl, Integer price,
        LocalDateTime regDateTime) {
        this.id = id;
        this.transactionId = transactionId;
        this.shopCode = shopCode;
        this.category = category;
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.price = price;
        this.regDateTime = regDateTime;
    }

    static CrawlingProductEntity of(CrawlingProduct crawlingProduct) {
        return CrawlingProductEntity.builder()
            .id(crawlingProduct.getId())
            .transactionId(crawlingProduct.getTransactionId())
            .shopCode(crawlingProduct.getShopCode())
            .productId(crawlingProduct.getProductId())
            .productName(crawlingProduct.getProductName())
            .price(crawlingProduct.getPrice())
            .imgUrl(crawlingProduct.getImgUrl())
            .productUrl(crawlingProduct.getProductUrl())
            .category(crawlingProduct.getCategory())
            .regDateTime(crawlingProduct.getRegDateTime())
            .build();
    }

    public void updateFieldSize() {
        if (productId.length() > 30) {
            productId = productId.substring(0, 30);
        }
        if (productName.length() > 100) {
            productName = productName.substring(0, 100);
        }
        if (imgUrl.length() > 100) {
            imgUrl = imgUrl.substring(0, 100);
        }
        if (productUrl.length() > 100) {
            productUrl = productUrl.substring(0, 100);
        }
    }

    CrawlingProduct toDomain() {
        return CrawlingProduct.builder()
            .id(id)
            .transactionId(transactionId)
            .shopCode(shopCode)
            .productId(productId)
            .productName(productName)
            .price(price)
            .imgUrl(imgUrl)
            .productUrl(productUrl)
            .category(category)
            .regDateTime(regDateTime)
            .build();
    }
}
