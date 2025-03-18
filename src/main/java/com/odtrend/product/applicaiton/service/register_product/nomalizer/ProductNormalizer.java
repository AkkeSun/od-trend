package com.odtrend.product.applicaiton.service.register_product.nomalizer;

import com.odtrend.product.domain.Product;
import java.util.List;

public interface ProductNormalizer {

    List<Product> normalize(String productInfo);
}
