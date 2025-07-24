package com.odtrend.applicaiton.service.register_product;

import com.odtrend.IntegrationTestSupport;
import com.odtrend.adapter.out.persistence.crawling.crawlingLog.CrawlingLogEntity;
import com.odtrend.adapter.out.persistence.crawling.crawlingLog.CrawlingLogRepository;
import com.odtrend.adapter.out.persistence.crawling.crawlingProduct.CrawlingProductEntity;
import com.odtrend.adapter.out.persistence.crawling.crawlingProduct.CrawlingProductRepository;
import com.odtrend.adapter.out.persistence.crawling.errorLog.ErrorLogEntity;
import com.odtrend.adapter.out.persistence.crawling.errorLog.ErrorLogRepository;
import com.odtrend.domain.model.CrawlingPage;
import java.io.IOException;
import java.util.List;
import jdk.jfr.Description;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegisterProductAsyncWorkerTest extends IntegrationTestSupport {

    @Autowired
    RegisterProductAsync worker;
    @Autowired
    CrawlingProductRepository crawlingProductRepository;
    @Autowired
    CrawlingLogRepository crawlingLogRepository;
    @Autowired
    ErrorLogRepository errorLogRepository;

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8090);
    }

    @AfterEach
    public void shutdown() throws IOException {
        crawlingProductRepository.deleteAll();
        crawlingLogRepository.deleteAll();
        errorLogRepository.deleteAll();
        mockWebServer.shutdown();
    }

    @Nested
    @DisplayName("[registerProduct] 수집한 상품을 등록하는 메소드")
    class Describe_registerProduct {

        @Test
        @Description("[error] 데이터 수집중 오류 발생시 에러 로그를 등록한다.")
        void error1() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test").toString())
                .shopCode("15")
                .method("GET")
                .build();
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

            // when
            worker.registerProduct(crawlingPage);

            //then
            for (ErrorLogEntity entity : errorLogRepository.findAll()) {
                assert entity.getErrorCode() == 2001;
                assert entity.getErrorMessage().equals("API 호출에 실패했습니다");
                assert entity.getDomain().equals("register_product");
            }
        }

        @Test
        @Description("[error] 등록되지 않은 쇼핑몰의 경우 예외를 발생시킨다.")
        void error2() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test2").toString())
                .shopCode("12")
                .method("GET")
                .build();
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("success"));

            // when
            worker.registerProduct(crawlingPage);

            // then
            for (ErrorLogEntity entity : errorLogRepository.findAll()) {
                assert entity.getErrorCode() == 2002;
                assert entity.getErrorMessage().equals("상품 정규화 팩토리 호출에 실패했습니다");
                assert entity.getDomain().equals("register_product");
            }
        }


        @Test
        @Description("[error] 상품 데이터 표준화중 오류 발생시 에러 로그를 등록한다.")
        void error3() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test2").toString())
                .shopCode("15")
                .method("GET")
                .build();
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("success"));

            // when
            worker.registerProduct(crawlingPage);

            //then
            for (ErrorLogEntity entity : errorLogRepository.findAll()) {
                assert entity.getErrorCode() == 2003;
                assert entity.getErrorMessage().equals("상품 정규화에 실패했습니다");
                assert entity.getDomain().equals("register_product");
            }
        }

        @Test
        @Description("[success] 상품 수집 로우 데이터와 정제 데이터가 정상적으로 등록되는지 확인한다.")
        void success() throws InterruptedException {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test2").toString())
                .shopCode("15")
                .method("GET")
                .build();
            String crawlingResult = "{\"products\": [{\"isAd\": false, \"rank\": 1, \"nvMid\": \"84866663302\", \"productId\": \"84866663302\", \"chnlProductId\": \"7322162980\", \"title\": \"[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/7322162980\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 800000, \"price\": \"800,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.9\", \"reviewCount\": \"17,597\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"수면밀도\", \"mallLinkUrl\": \"https://smartstore.naver.com/sleepmildo\", \"discountPriceValue\": 289000, \"discountPrice\": \"289,000\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 101703739}, {\"isAd\": false, \"rank\": 2, \"nvMid\": \"82250264065\", \"productId\": \"82250264065\", \"chnlProductId\": \"4705743207\", \"title\": \"[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/4705743207\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 58000, \"price\": \"58,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.8\", \"reviewCount\": \"80,417\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"내가그린집\", \"mallLinkUrl\": \"https://smartstore.naver.com/thedreamhouse\", \"discountPriceValue\": 20900, \"discountPrice\": \"20,900\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 100225466}], \"syncDate\": \"20250409\"}";

            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(crawlingResult)
                .setHeader("Content-Type", "application/json"));

            // when
            worker.registerProduct(crawlingPage);

            // then
            assert errorLogRepository.findAll().isEmpty();
            List<CrawlingLogEntity> logs = crawlingLogRepository.findAll();
            assert logs.size() == 1;

            List<CrawlingProductEntity> savedProduct = crawlingProductRepository.findAll();
            assert savedProduct.size() == 2;
            assert savedProduct.getFirst().getShopCode().equals(crawlingPage.shopCode());
            assert savedProduct.get(1).getShopCode().equals(crawlingPage.shopCode());
            assert savedProduct.getFirst().getProductId().equals("84866663302");
            assert savedProduct.get(1).getProductId().equals("82250264065");
            assert savedProduct.getFirst().getProductName().equals(
                "[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)");
            assert savedProduct.get(1).getProductName().equals(
                "[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작");
            assert savedProduct.getFirst().getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450");
            assert savedProduct.get(1).getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450");
            assert savedProduct.getFirst().getProductUrl().equals(
                "https://smartstore.naver.com/main/products/7322162980");
            assert savedProduct.get(1).getProductUrl().equals(
                "https://smartstore.naver.com/main/products/4705743207");
        }

        @Test
        @Description("[success] 등록된 상품은 새로 등록되지 않도록 처리한다.")
        void success2() throws InterruptedException {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url(mockWebServer.url("/test2").toString())
                .shopCode("15")
                .method("GET")
                .build();
            String crawlingResult = "{\"products\": [{\"isAd\": false, \"rank\": 1, \"nvMid\": \"84866663302\", \"productId\": \"84866663302\", \"chnlProductId\": \"7322162980\", \"title\": \"[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/7322162980\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 800000, \"price\": \"800,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.9\", \"reviewCount\": \"17,597\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"수면밀도\", \"mallLinkUrl\": \"https://smartstore.naver.com/sleepmildo\", \"discountPriceValue\": 289000, \"discountPrice\": \"289,000\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 101703739}, {\"isAd\": false, \"rank\": 2, \"nvMid\": \"82250264065\", \"productId\": \"82250264065\", \"chnlProductId\": \"4705743207\", \"title\": \"[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/4705743207\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 58000, \"price\": \"58,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.8\", \"reviewCount\": \"80,417\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"내가그린집\", \"mallLinkUrl\": \"https://smartstore.naver.com/thedreamhouse\", \"discountPriceValue\": 20900, \"discountPrice\": \"20,900\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 100225466}], \"syncDate\": \"20250409\"}";

            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(crawlingResult)
                .setHeader("Content-Type", "application/json"));

            crawlingProductRepository.save(CrawlingProductEntity.builder()
                .shopCode(crawlingPage.shopCode())
                .productId("84866663302")
                .productName("[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)")
                .imgUrl(
                    "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450")
                .productUrl("https://smartstore.naver.com/main/products/7322162980")
                .build());

            // when
            worker.registerProduct(crawlingPage);

            // then
            assert errorLogRepository.findAll().isEmpty();
            List<CrawlingLogEntity> logs = crawlingLogRepository.findAll();
            assert logs.size() == 1;

            List<CrawlingProductEntity> savedProduct = crawlingProductRepository.findAll();
            assert savedProduct.size() == 2;
            assert savedProduct.getFirst().getShopCode().equals(crawlingPage.shopCode());
            assert savedProduct.get(1).getShopCode().equals(crawlingPage.shopCode());
            assert savedProduct.getFirst().getProductId().equals("84866663302");
            assert savedProduct.get(1).getProductId().equals("82250264065");
            assert savedProduct.getFirst().getProductName().equals(
                "[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)");
            assert savedProduct.get(1).getProductName().equals(
                "[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작");
            assert savedProduct.getFirst().getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450");
            assert savedProduct.get(1).getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450");
            assert savedProduct.getFirst().getProductUrl().equals(
                "https://smartstore.naver.com/main/products/7322162980");
            assert savedProduct.get(1).getProductUrl().equals(
                "https://smartstore.naver.com/main/products/4705743207");
        }
    }
}