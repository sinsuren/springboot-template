package com.sinsuren.sample.springboottemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@Slf4j
public class SampleService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            ignoreExceptions = HttpClientErrorException.class,
            fallbackMethod = "getSecondDetailFallback",
            threadPoolKey = "second-service",
            commandKey = "second-service"
    )
    public String getSecondDetail(final String userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:50000").pathSegment("v1",
                "test", "second", userId);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
                String.class);
        return response.getBody();
    }


    public String getSecondDetailFallback(final String userId) {
        log.info("Falling back to fallback method");
        return UUID.randomUUID().toString();
    }

}
