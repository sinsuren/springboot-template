package com.sinsuren.sample.springboottemplate;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Optional;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        addHeader(request, "X-Request-Id");
        addHeader(request, "X-Transaction-Id");
        return execution.execute(request, body);
    }

    private void addHeader(HttpRequest request, String header) {
        if (!Optional.ofNullable(request.getHeaders().get(header)).map(arr -> arr.get(0)).isPresent() && StringUtils.isNotBlank(MDC.get(header))) {
            request.getHeaders().set(header, MDC.get(header));
        }
    }
}
