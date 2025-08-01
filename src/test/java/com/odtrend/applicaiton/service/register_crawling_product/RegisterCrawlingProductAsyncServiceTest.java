package com.odtrend.applicaiton.service.register_crawling_product;

import com.odtrend.applicaiton.port.in.RegisterCrawlingProductAsyncUseCase;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.domain.model.CrawlingProduct;
import com.odtrend.domain.model.ErrorLog;
import com.odtrend.domain.service.NaverProductNormalizer;
import com.odtrend.fakeClass.FakeCrawlingLogStoragePort;
import com.odtrend.fakeClass.FakeCrawlingProductStoragePort;
import com.odtrend.fakeClass.FakeErrorLogStoragePort;
import com.odtrend.fakeClass.StubShopClientPort;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegisterCrawlingProductAsyncServiceTest {

    private final StubShopClientPort shopClientPort;
    private final FakeErrorLogStoragePort errorLogStoragePort;
    private final FakeCrawlingLogStoragePort crawlingLogStoragePort;
    private final FakeCrawlingProductStoragePort crawlingProductStoragePort;
    private final RegisterCrawlingProductAsyncUseCase useCase;

    RegisterCrawlingProductAsyncServiceTest() {
        shopClientPort = new StubShopClientPort();
        errorLogStoragePort = new FakeErrorLogStoragePort();
        crawlingLogStoragePort = new FakeCrawlingLogStoragePort();
        crawlingProductStoragePort = new FakeCrawlingProductStoragePort();
        useCase = new RegisterCrawlingProductAsyncService(
            shopClientPort,
            errorLogStoragePort,
            crawlingLogStoragePort,
            crawlingProductStoragePort
        );
    }

    @AfterEach
    public void reset() {
        errorLogStoragePort.database.clear();
        crawlingLogStoragePort.database.clear();
        crawlingProductStoragePort.database.clear();
    }

    @Nested
    @DisplayName("[registerProduct] 수집한 상품을 등록하는 메소드")
    class Describe_registerProduct {

        @Test
        @Description("[error] 데이터 수집중 오류 발생시 에러 로그를 등록한다.")
        void error1() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url("http://test.com")
                .shopCode("99")
                .method("GET")
                .build();

            // when
            useCase.registerProduct(crawlingPage);

            //then
            ErrorLog errorLog = errorLogStoragePort.database.getFirst();
            assert errorLog.errorCode() == 2001;
            assert errorLog.errorMessage().equals("API 호출에 실패했습니다");
            assert errorLog.domain().equals("register_product");
        }

        @Test
        @Description("[error] 등록되지 않은 쇼핑몰의 경우 예외를 발생시킨다.")
        void error2() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url("http://test.com")
                .shopCode("04")
                .method("GET")
                .build();

            // when
            useCase.registerProduct(crawlingPage);

            // then
            ErrorLog errorLog = errorLogStoragePort.database.getFirst();
            assert errorLog.errorCode() == 2002;
            assert errorLog.errorMessage().equals("상품 정규화 팩토리 호출에 실패했습니다");
            assert errorLog.domain().equals("register_product");
        }


        @Test
        @Description("[error] 상품 데이터 표준화중 오류 발생시 에러 로그를 등록한다.")
        void error3() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url("error")
                .shopCode("15")
                .method("GET")
                .build();

            // when
            useCase.registerProduct(crawlingPage);

            // when
            ErrorLog errorLog = errorLogStoragePort.database.getFirst();
            assert errorLog.errorCode() == 2003;
            assert errorLog.errorMessage().equals("상품 정규화에 실패했습니다");
            assert errorLog.domain().equals("register_product");
        }

        @Test
        @Description("[success] 상품 수집 로우 데이터와 정제 데이터가 정상적으로 등록되는지 확인한다.")
        void success() {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url("http://success.com")
                .shopCode("15")
                .method("GET")
                .build();

            // when
            useCase.registerProduct(crawlingPage);

            // then
            assert crawlingProductStoragePort.database.size() == 2;
            CrawlingProduct first = crawlingProductStoragePort.database.getFirst();
            CrawlingProduct last = crawlingProductStoragePort.database.getLast();
            assert first.getShopCode().equals(crawlingPage.shopCode());
            assert last.getShopCode().equals(crawlingPage.shopCode());
            assert first.getProductId().equals("84866663302");
            assert last.getProductId().equals("82250264065");
            assert first.getProductName().equals(
                "[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)");
            assert last.getProductName().equals(
                "[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작");
            assert first.getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450");
            assert last.getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450");
            assert first.getProductUrl().equals(
                "https://smartstore.naver.com/main/products/7322162980");
            assert last.getProductUrl().equals(
                "https://smartstore.naver.com/main/products/4705743207");
        }

        @Test
        @Description("[success] 등록된 상품은 새로 등록되지 않도록 처리한다.")
        void success2() throws InterruptedException {
            // given
            CrawlingPage crawlingPage = CrawlingPage.builder()
                .url("http://success.com")
                .shopCode("15")
                .method("GET")
                .build();
            String crawlingResult = "{\"products\": [{\"isAd\": false, \"rank\": 1, \"nvMid\": \"84866663302\", \"productId\": \"84866663302\", \"chnlProductId\": \"7322162980\", \"title\": \"[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/7322162980\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 800000, \"price\": \"800,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.9\", \"reviewCount\": \"17,597\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"수면밀도\", \"mallLinkUrl\": \"https://smartstore.naver.com/sleepmildo\", \"discountPriceValue\": 289000, \"discountPrice\": \"289,000\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 101703739}, {\"isAd\": false, \"rank\": 2, \"nvMid\": \"82250264065\", \"productId\": \"82250264065\", \"chnlProductId\": \"4705743207\", \"title\": \"[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/4705743207\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 58000, \"price\": \"58,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.8\", \"reviewCount\": \"80,417\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"내가그린집\", \"mallLinkUrl\": \"https://smartstore.naver.com/thedreamhouse\", \"discountPriceValue\": 20900, \"discountPrice\": \"20,900\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 100225466}], \"syncDate\": \"20250409\"}";
            crawlingProductStoragePort.saveAll(new NaverProductNormalizer().normalize(crawlingPage,
                "1234", crawlingResult));

            // when
            useCase.registerProduct(crawlingPage);

            // then
            assert crawlingProductStoragePort.database.size() == 2;
            CrawlingProduct first = crawlingProductStoragePort.database.getFirst();
            CrawlingProduct last = crawlingProductStoragePort.database.getLast();
            assert first.getShopCode().equals(crawlingPage.shopCode());
            assert last.getShopCode().equals(crawlingPage.shopCode());
            assert first.getProductId().equals("84866663302");
            assert last.getProductId().equals("82250264065");
            assert first.getProductName().equals(
                "[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)");
            assert last.getProductName().equals(
                "[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작");
            assert first.getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450");
            assert last.getImgUrl().equals(
                "https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450");
            assert first.getProductUrl().equals(
                "https://smartstore.naver.com/main/products/7322162980");
            assert last.getProductUrl().equals(
                "https://smartstore.naver.com/main/products/4705743207");
        }
    }
}