package com.odtrend.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Set<String> keywords;
    private LocalDateTime regDateTime;

    @Builder
    public CrawlingProduct(Long id, String transactionId, String shopCode, Category category,
        String productId, String productName, String imgUrl, String productUrl, int price,
        Set<String> keywords, LocalDateTime regDateTime) {
        this.id = id;
        this.transactionId = transactionId;
        this.shopCode = shopCode;
        this.category = category;
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productUrl = productUrl;
        this.price = price;
        this.keywords = keywords;
        this.regDateTime = regDateTime;
    }

    public boolean isValid() {
        return StringUtils.hasText(productId) && StringUtils.hasText(productName) &&
            StringUtils.hasText(imgUrl) && StringUtils.hasText(productUrl) && price > 0;
    }

    @JsonIgnore
    public String getKeywordQueryDocument() {
        return String.format("""
                    너는 이커머스 상품 정보에서 고객의 검색 의도에 맞는 키워드를 추출하는 SEO 전문가야.
                    아래에 제공되는 '상품명', '상품 카테고리', '상품 금액' 정보를 바탕으로, 고객들이 검색할 만한 핵심 키워드 10개를 추출해 줘. 

                    * 상품명: %s
                    * 상품 카테고리: %s
                    * 상품 금액: %s
                                
                    결과는 키워드1, 키워드2, ... 형태로 출력해야 한다.
                    상품의 특징, 소재, 스타일, 타겟 고객, 사용 상황을 종합적으로 고려하고, 다른 설명은 절대 붙이지 마라.
                """,
                productName, category.name(), price
        );
    }

    @JsonIgnore
    public void updateKeywords(String keyword) {
        this.keywords = Set.of(keyword.replace("\n", "").split(","));
    }

}
