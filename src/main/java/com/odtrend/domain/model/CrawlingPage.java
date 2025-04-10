package com.odtrend.domain.model;

import java.util.List;
import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
public record CrawlingPage(
    Long id,
    String shopCode,
    String url,
    String method,
    String body,
    Category category,
    List<CrawlingPageHeader> headers,
    String useYn
) {

    public MultiValueMap<String, String> toHeadersMap() {
        MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
        if (headers != null) {
            for (CrawlingPageHeader header : headers) {
                headersMap.add(header.headerKey(), header.headerValue());
            }
        }
        return headersMap;
    }
}
