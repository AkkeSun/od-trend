package com.odtrend.domain.model;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

class CrawlingPageTest {

    @Nested
    @DisplayName("[toHeaderMap] 페이지 헤더를 맵으로 반환하는 메소드")
    class ToHeaderMapTest {

        @DisplayName("[success] 헤더 정보가 있다면 해당 정보를 맵으로 반환한다.")
        void success() {
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .headers(
                    List.of(
                        CrawlingPageHeader.builder()
                            .headerKey("key1")
                            .headerValue("value1")
                            .build(),
                        CrawlingPageHeader.builder()
                            .headerKey("key2")
                            .headerValue("value2")
                            .build()
                    )
                )
                .build();

            MultiValueMap<String, String> headerMap = crawlingPage.toHeadersMap();
            assert headerMap.size() == 2;
            assert headerMap.get("key1").get(0).equals("value1");
            assert headerMap.get("key2").get(0).equals("value2");
        }

        @Test
        @DisplayName("[success] 헤더 정보가 없다면 빈 맵을 반환한다.")
        void noHeader() {
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .headers(null)
                .build();

            MultiValueMap<String, String> headerMap = crawlingPage.toHeadersMap();
            assert headerMap.size() == 0;
        }
    }
}