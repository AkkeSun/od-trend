package com.odtrend.product.applicaiton.service.register_product;

import com.odtrend.product.applicaiton.port.in.RegisterProductUseCase;
import com.odtrend.product.applicaiton.port.out.FindShopInfoPort;
import com.odtrend.product.applicaiton.service.register_product.nomalizer.ProductNormalizer;
import com.odtrend.product.domain.ShopInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterProductService implements RegisterProductUseCase {

    private final FindShopInfoPort findShopInfoPort;
    private final ProductNormalizer productNormalizer;

    @Override
    public void registerProduct() {
        // ShopInfo 조회
        List<ShopInfo> shopInfos = findShopInfoPort.findAll();

        // ------- 다중 스레드 처리 --------
        // Product Api 조회

        // 표준화

        // 저장
        // ------------------------------
    }
}