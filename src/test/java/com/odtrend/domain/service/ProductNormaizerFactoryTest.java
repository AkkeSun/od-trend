package com.odtrend.domain.service;

import static com.odtrend.domain.service.ProductNormaizerFactory.getNormalizer;
import static com.odtrend.infrastructure.exception.ErrorCode.Product_Nomalizer_Factory_Error;
import static org.junit.Assert.assertThrows;

import com.odtrend.infrastructure.exception.CustomBusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductNormaizerFactoryTest {


    @Nested
    @DisplayName("[getNormalizer] 상품 표준화 객채를 가져오는 메소드")
    class Describe_getNormalizer {

        @Test
        @DisplayName("[success] 등록된 쇼핑몰이라면 해당 쇼핑몰의 표준화 객채를 응답한다.")
        void success() {
            // given
            String shopCode = "15";

            // when
            ProductNormalizer productNormalizer = getNormalizer(shopCode);

            // then
            assert productNormalizer != null;
        }

        @Test
        @DisplayName("[error] 등록된 쇼핑몰이 아니라면 예외를 응답한다.")
        void error() {
            // given
            String shopCode = "testShop";

            // when
            CustomBusinessException exception = assertThrows(CustomBusinessException.class, () ->
                getNormalizer(shopCode));

            // then
            assert exception.getErrorCode().equals(Product_Nomalizer_Factory_Error);
        }
    }
}