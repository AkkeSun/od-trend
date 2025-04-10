package com.odtrend.domain.model;

import lombok.Builder;

@Builder
public record CrawlingPageHeader(
    Long id,
    String headerKey,
    String headerValue
) {

}
