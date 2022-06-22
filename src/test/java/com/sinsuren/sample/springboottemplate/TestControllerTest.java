package com.sinsuren.sample.springboottemplate;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class TestControllerTest {

    @Test
    void createMultipleRestCall() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final AtomicLong counter = new AtomicLong(1);
        for (int i = 0; i < 1000000; i++) {
            final Long requestCounter = counter.getAndIncrement();
            executorService.submit(() -> makeNetworkCall(requestCounter));
            if (i % 10000 == 0) {
                TimeUnit.SECONDS.sleep(1);
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);
    }


    private void makeNetworkCall(Long requestNumber) {
        try {

            URL url = new URL("http://localhost:50000/v1/test/first/" + requestNumber);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(10);
            httpConn.setReadTimeout(100);
            httpConn.setRequestMethod("GET");

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";

            System.out.println(response);
        } catch (IOException e) {
            System.out.println(e);
            //ignore exceptions
        }
    }
}