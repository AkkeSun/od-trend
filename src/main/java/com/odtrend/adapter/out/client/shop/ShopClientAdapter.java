package com.odtrend.adapter.out.client.shop;

import static com.odtrend.infrastructure.exception.ErrorCode.Client_Call_Error;

import com.odtrend.applicaiton.port.out.ShopClientPort;
import com.odtrend.domain.model.CrawlingPage;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
class ShopClientAdapter implements ShopClientPort {

    private final ClientHttpRequestFactorySettings settings;

    ShopClientAdapter() {
        this.settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(Duration.ofSeconds(5))
            .withReadTimeout(Duration.ofSeconds(10));
    }

    @Override
    public String getProducts(CrawlingPage crawlingPage) {
        RestClient restClient = RestClient.builder()
            .baseUrl(crawlingPage.url())
            .requestFactory(ClientHttpRequestFactories.get(settings))
            .build();

        try {
            String response = crawlingPage.method().equals("GET") ?
                restClient.get()
                    .headers(headers -> headers.addAll(crawlingPage.toHeadersMap()))
                    .retrieve()
                    .body(String.class) :
                restClient.post()
                    .headers(headers -> headers.addAll(crawlingPage.toHeadersMap()))
                    .body(crawlingPage.body())
                    .retrieve()
                    .body(String.class);

            if (!StringUtils.hasText(response)) {
                throw new CustomBusinessException(Client_Call_Error);
            }

            return response;
        } catch (Exception e) {
            log.error("ShopClientAdapter error: {}", e.getMessage());
            throw new CustomBusinessException(Client_Call_Error);
        }
    }
}
