package com.odtrend.domain.service;

import static com.odtrend.infrastructure.exception.ErrorCode.Product_Nomalizer_Error;
import static org.junit.Assert.assertThrows;

import com.odtrend.domain.model.Category;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NaverProductNormalizerTest {

    NaverProductNormalizer normalize = new NaverProductNormalizer();

    @Nested
    @DisplayName("[normalize] 네이버 수집 상품 표준화 메소드")
    class Describe_normalize {

        @Test
        @DisplayName("[success] 상품 정보를 정상적으로 표준화한다.")
        void success() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .shopCode("15")
                .category(Category.DIGITAL)
                .build();
            String transactionId = "transaction-id";
            String crawlingResult = "{\"products\": [{\"isAd\": false, \"rank\": 1, \"nvMid\": \"84866663302\", \"productId\": \"84866663302\", \"chnlProductId\": \"7322162980\", \"title\": \"[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/7322162980\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 800000, \"price\": \"800,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.9\", \"reviewCount\": \"17,597\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"수면밀도\", \"mallLinkUrl\": \"https://smartstore.naver.com/sleepmildo\", \"discountPriceValue\": 289000, \"discountPrice\": \"289,000\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 101703739}, {\"isAd\": false, \"rank\": 2, \"nvMid\": \"82250264065\", \"productId\": \"82250264065\", \"chnlProductId\": \"4705743207\", \"title\": \"[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/4705743207\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 58000, \"price\": \"58,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.8\", \"reviewCount\": \"80,417\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"내가그린집\", \"mallLinkUrl\": \"https://smartstore.naver.com/thedreamhouse\", \"discountPriceValue\": 20900, \"discountPrice\": \"20,900\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 100225466}], \"syncDate\": \"20250409\"}";

            // when
            List<CrawlingProduct> result = normalize.normalize(crawlingPage,
                transactionId, crawlingResult);

            // then
            assert result.size() == 2;
            assert result.get(0).getProductId().equals("84866663302");
            assert result.get(0).getProductName()
                .equals("[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)");
            assert result.get(0).getImgUrl()
                .equals(
                    "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450");
            assert result.get(0).getProductUrl()
                .equals("https://smartstore.naver.com/main/products/7322162980");
            assert result.get(0).getPrice() == 800000;
            assert result.get(0).getShopCode().equals("15");
            assert result.get(0).getCategory().equals(Category.DIGITAL);
            assert result.get(0).getTransactionId().equals(transactionId);
            assert result.get(1).getProductId().equals("82250264065");
            assert result.get(1).getProductName()
                .equals("[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작");
            assert result.get(1).getImgUrl()
                .equals(
                    "https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450");
            assert result.get(1).getProductUrl()
                .equals("https://smartstore.naver.com/main/products/4705743207");
            assert result.get(1).getPrice() == 58000;
            assert result.get(1).getShopCode().equals("15");
            assert result.get(1).getCategory().equals(Category.DIGITAL);
            assert result.get(1).getTransactionId().equals(transactionId);
        }

        @Test
        @DisplayName("[error] 표준화 중 오류 발생시 예외를 응답한다.")
        void error() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .shopCode("15")
                .category(Category.DIGITAL)
                .build();
            String transactionId = "transaction-id";
            String crawlingResult = "error";

            // when
            CustomBusinessException exception = assertThrows(CustomBusinessException.class, () -> {
                normalize.normalize(crawlingPage, transactionId, crawlingResult);
            });

            // then
            assert exception.getErrorCode().equals(Product_Nomalizer_Error);
        }
    }
}