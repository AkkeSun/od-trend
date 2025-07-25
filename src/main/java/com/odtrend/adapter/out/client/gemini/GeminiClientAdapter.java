package com.odtrend.adapter.out.client.gemini;

import static com.odtrend.infrastructure.exception.ErrorCode.Client_Call_Error;

import com.odtrend.applicaiton.port.out.GeminiClientPort;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Component
class GeminiClientAdapter implements GeminiClientPort {

    private final GeminiClient client;
    GeminiClientAdapter(@Value("${service-constant.external.gemini.host}") String host,
                        @Value("${service-constant.external.gemini.token}") String token) {
        RestClient restClient = RestClient.builder()
                .baseUrl(host)
                .defaultHeader("x-goog-api-key", "AIzaSyBByq3D3xAtgsQsZ_GujWYZYvV286MTjD0")
                .requestFactory(ClientHttpRequestFactories.get(
                        ClientHttpRequestFactorySettings.DEFAULTS
                                .withConnectTimeout(Duration.ofSeconds(1))
                                .withReadTimeout(Duration.ofSeconds(5))))
                .build();

        this.client = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClient))
                .build()
                .createClient(GeminiClient.class);
    }

    @Override
    public float[] embedding(String document) {
        try {
            GeminiEmbeddingResponse result = client.embedding(GeminiEmbeddingRequest.of(document));
            return result.getResponse();
        } catch (Exception e) {
            log.error("ShopClientAdapter error: {}", e.getMessage());
            throw new CustomBusinessException(Client_Call_Error);
        }
    }
}
