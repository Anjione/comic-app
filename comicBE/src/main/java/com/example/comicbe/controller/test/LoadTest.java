package com.example.comicbe.controller.test;

import com.example.comicbe.payload.dto.MangaDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class LoadTest {
    @GetMapping("/test-load")
    public void test() throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://138.201.52.164:8081/api/manga/166";

        int totalRequests = 1000000;

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        CountDownLatch latch = new CountDownLatch(1); // để start đồng loạt
        CountDownLatch doneLatch = new CountDownLatch(totalRequests); // để chờ tất cả kết thúc

        for (int i = 0; i < totalRequests; i++) {
            executor.submit(() -> {
                try {
                    latch.await(); // chờ tín hiệu start
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .GET()
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.statusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        long start = System.currentTimeMillis();
        latch.countDown(); // tất cả thread start cùng lúc
        doneLatch.await(); // chờ tất cả xong
        long end = System.currentTimeMillis();

        System.out.println("All requests finished in " + (end - start) + " ms");
        executor.shutdown();
    }
}
