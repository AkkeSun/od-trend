package com.odtrend.adapter.out.client.elasticsearch;

import static com.odtrend.infrastructure.exception.ErrorCode.Client_Call_Error;
import static com.odtrend.infrastructure.util.JsonUtil.toJsonString;

import com.odtrend.applicaiton.port.out.ElasticSearchClientPort;
import com.odtrend.infrastructure.exception.CustomBusinessException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
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
class ElasticSearchClientAdapter implements ElasticSearchClientPort {

    private final ElasticSearchClient client;

    ElasticSearchClientAdapter(
        @Value("${service-constant.external.elasticsearch.host}") String host
    ) {
        RestClient restClient = RestClient.builder()
            .baseUrl(host)
            .requestFactory(ClientHttpRequestFactories.get(
                ClientHttpRequestFactorySettings.DEFAULTS
                    .withConnectTimeout(Duration.ofSeconds(1))
                    .withReadTimeout(Duration.ofSeconds(5))))
            .build();

        this.client = HttpServiceProxyFactory.builder()
            .exchangeAdapter(RestClientAdapter.create(restClient))
            .build()
            .createClient(ElasticSearchClient.class);
        ;
    }

    @Override
    public List<Long> findIdByEmbeddingAndCategory(float[] embedding, String category) {
        try {
            String request = toJsonString(FindProductEsByEmbeddingRequest.of(embedding, category));
            FindProductsEsResponse response = client.findProducts(request);
            return response.isEmpty() ? Collections.emptyList() :
                response.hits().hits().stream().map(da -> da._source().productId()).toList();
            
        } catch (Exception e) {
            log.error("ElasticSearchClientAdapter error: {}", e.getMessage());
            throw new CustomBusinessException(Client_Call_Error);
        }
    }
}
