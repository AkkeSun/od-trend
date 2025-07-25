package com.odtrend.fakeClass;

import static com.odtrend.infrastructure.exception.ErrorCode.Client_Call_Error;

import com.odtrend.applicaiton.port.out.ShopClientPort;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.infrastructure.exception.CustomBusinessException;

public class StubShopClientPort implements ShopClientPort {

    @Override
    public String getProducts(CrawlingPage crawlingPage) {
        if (crawlingPage.shopCode().equals("15")) {
            return crawlingPage.url().contains("error") ? "error"
                : "{\"products\": [{\"isAd\": false, \"rank\": 1, \"nvMid\": \"84866663302\", \"productId\": \"84866663302\", \"chnlProductId\": \"7322162980\", \"title\": \"[수면밀도] 허리 환자가 직접 만든 매트리스 허리에 좋은 침대 메트리스 미디엄 하드 싱글(S)\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8486666/84866663302.6.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/7322162980\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 800000, \"price\": \"800,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.9\", \"reviewCount\": \"17,597\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"수면밀도\", \"mallLinkUrl\": \"https://smartstore.naver.com/sleepmildo\", \"discountPriceValue\": 289000, \"discountPrice\": \"289,000\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 101703739}, {\"isAd\": false, \"rank\": 2, \"nvMid\": \"82250264065\", \"productId\": \"82250264065\", \"chnlProductId\": \"4705743207\", \"title\": \"[내가그린집] 히든형상기억암막커튼 핀형 150x200 맞춤제작\", \"imageUrl\": \"https://shopping-phinf.pstatic.net/main_8225026/82250264065.9.jpg?type=f450\", \"linkUrl\": \"https://smartstore.naver.com/main/products/4705743207\", \"discountRate\": \"63\", \"discountRateUnit\": \"%\", \"priceValue\": 58000, \"price\": \"58,000\", \"priceUnit\": \"원\", \"reviewScore\": \"4.8\", \"reviewCount\": \"80,417\", \"labels\": [\"무료배송\"], \"isZzim\": false, \"isArrivalGuarantee\": false, \"mallNm\": \"내가그린집\", \"mallLinkUrl\": \"https://smartstore.naver.com/thedreamhouse\", \"discountPriceValue\": 20900, \"discountPrice\": \"20,900\", \"discountPriceUnit\": \"원\", \"syncDate\": \"20250409\", \"chnlSeq\": 100225466}], \"syncDate\": \"20250409\"}";
        }
        if (crawlingPage.shopCode().equals("99")) {
            throw new CustomBusinessException(Client_Call_Error);
        }
        return "";
    }
}
