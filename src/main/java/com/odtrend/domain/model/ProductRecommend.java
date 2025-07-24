package com.odtrend.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductRecommend(
    Long productId,
    String type,
    LocalDate checkDate,
    LocalDate regDate,
    LocalDateTime regDateTime
) {

    public static ProductRecommend of(Long productId, LocalDate checkDate) {
        return ProductRecommend.builder()
            .productId(productId)
            .type("TREND")
            .checkDate(checkDate)
            .regDate(LocalDate.now())
            .regDateTime(LocalDateTime.now())
            .build();
    }
}
