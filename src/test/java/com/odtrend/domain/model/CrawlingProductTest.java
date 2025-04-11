package com.odtrend.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrawlingProductTest {

    @Nested
    @DisplayName("[isValid] 크롤링 상품의 유효성을 검증하는 메소드")
    class Describe_isValid {

        @Test
        @DisplayName("[success] 상품 아이디가 null 인 경우 false 를 응답한다.")
        void nullProductId() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId(null)
                .productName("test")
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            assert !result;
        }

        @Test
        @DisplayName("[success] 상품 아이디가 빈 문자열인 경우 false 를 응답한다.")
        void success2() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("")
                .productName("test")
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품명이 null 인 경우 false 를 응답한다.")
        void nullProductName() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName(null)
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품명이 빈 문자열인 경우 false 를 응답한다.")
        void emptyProductName() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("")
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품 이미지 URL 이 null 인 경우 false 를 응답한다.")
        void nullImgUrl() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl(null)
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품 이미지 URL 이 빈 문자열인 경우 false 를 응답한다.")
        void emptyImgUrl() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl("")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품 URL 이 null 인 경우 false 를 응답한다.")
        void nullProductUrl() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl("img-url")
                .productUrl(null)
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 상품 URL 이 빈 문자열인 경우 false 를 응답한다.")
        void emptyProductUrl() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl("img-url")
                .productUrl("")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }

        @Test
        @DisplayName("[success] 가격이 0 인 경우 false 를 응답한다.")
        void zeroPrice() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(0)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert !result;
        }
        
        @Test
        @DisplayName("[success] 모든 필드가 유효한 경우 true 를 응답한다.")
        void allValid() {
            // given
            CrawlingProduct crawlingProduct = CrawlingProduct.builder()
                .productId("product-id")
                .productName("test")
                .imgUrl("img-url")
                .productUrl("product-url")
                .price(500)
                .build();

            // when
            boolean result = crawlingProduct.isValid();

            // then
            assert result;
        }

    }
}