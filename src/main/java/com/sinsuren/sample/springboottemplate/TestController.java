package com.sinsuren.sample.springboottemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/test")
public class TestController {

    @Autowired
    private SampleService sampleService;

    @GetMapping(value = "/first/{id}")
    public ResponseEntity<Object> createMemoryLeak(@PathVariable("id") String firstId) {

//        log.info("Received Request for first id {}", firstId);
        String response = sampleService.getSecondDetail(firstId);


        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/second/{id}")
    public ResponseEntity<Object> secondUrl(@PathVariable("id") String secondId) {

        log.info("Received Request for second id {}", secondId);
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }


}
