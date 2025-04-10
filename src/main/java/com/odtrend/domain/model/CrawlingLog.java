package com.odtrend.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CrawlingLog(
    Long id,
    Long crawlingPageId,
    String transactionId,
    String crawlingData,
    LocalDateTime regDateTime
) {

}
