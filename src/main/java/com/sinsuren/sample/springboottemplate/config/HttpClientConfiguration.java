package com.sinsuren.sample.springboottemplate.config;

import com.sinsuren.sample.springboottemplate.RestTemplateHeaderModifierInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "http.client")
@Getter
@Setter
public class HttpClientConfiguration {
    private HttpClientConfig config;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();

//        builder.interceptors(new RestTemplateHeaderModifierInterceptor());
        RestTemplate restTemplate = builder.requestFactory(() -> createRequestFactory(config)).build();

        List<ClientHttpRequestInterceptor> interceptorList = restTemplate.getInterceptors();

        if (interceptorList == null) {
            interceptorList = new ArrayList<>();
        }

        interceptorList.add(new RestTemplateHeaderModifierInterceptor());

        restTemplate.setInterceptors(interceptorList);

        return restTemplate;
    }

    private ClientHttpRequestFactory createRequestFactory(final HttpClientConfig httpClientConfig) {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(HttpClientFactory.createHttpClient(httpClientConfig));
        return requestFactory;
    }
}
