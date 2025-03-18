package com.odtrend.product.applicaiton.port.out;

import com.odtrend.product.domain.ShopInfo;
import java.util.List;

public interface FindShopInfoPort {

    List<ShopInfo> findAll();

}
