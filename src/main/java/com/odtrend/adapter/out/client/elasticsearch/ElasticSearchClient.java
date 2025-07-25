package com.odtrend.adapter.out.client.elasticsearch;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ElasticSearchClient {

    @PostExchange(url = "/product/_search", contentType = MediaType.APPLICATION_JSON_VALUE)
    FindProductEsResponse findProducts(@RequestBody String request);
}
