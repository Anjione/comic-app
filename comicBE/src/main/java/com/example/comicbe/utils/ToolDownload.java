package com.example.comicbe.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Semaphore;

@Slf4j
@Component
public class ToolDownload {
    private static final Random random = new Random();

    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    };

    private static final Semaphore semaphore = new Semaphore(1000);


    @Value("${crawler.cover-image-dir}")
    private static String coverImageDir = "./covers";

    @Autowired
    private WebClient webClient;


    private static final int MAX_RETRIES = 20;
    private static final int BASE_DELAY_MS = 1000;

    public Document fetchWithCloudflareBypass(String url) throws Exception {
        // Random delay trước khi request
//        Thread.sleep(500 + random.nextInt(1000));

//        Connection connection = Jsoup.connect(url)
//                // ✅ User Agent giống browser thật
//                .userAgent(USER_AGENTS[random.nextInt(USER_AGENTS.length)])
//
//                // ✅ Headers quan trọng
//                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
//                .header("Accept-Language", "en-US,en;q=0.9")
//                .header("Accept-Encoding", "gzip, deflate, br")
//                .header("Connection", "keep-alive")
//                .header("Upgrade-Insecure-Requests", "1")
//                .header("Sec-Fetch-Dest", "document")
//                .header("Sec-Fetch-Mode", "navigate")
//                .header("Sec-Fetch-Site", "none")
//                .header("Sec-Fetch-User", "?1")
//                .header("Cache-Control", "max-age=0")
//
//                // ✅ Referer nếu cần
//                .referrer("https://www.google.com/")
//
//                // ✅ Timeout dài hơn
//                .timeout(30000)
//
//                // ✅ Follow redirects
//                .followRedirects(true)
//                .maxBodySize(0) // Không giới hạn body size
//
//                // ✅ Ignore content type (quan trọng!)
//                .ignoreContentType(false)
//
//                // ✅ Ignore HTTP errors để xử lý thủ công
//                .ignoreHttpErrors(false);
//
//        // Execute request
//        Connection.Response response = connection.execute();
//
//        // Kiểm tra status code
//        if (response.statusCode() == 403 || response.statusCode() == 503) {
//            throw new Exception("Cloudflare blocked: HTTP " + response.statusCode());
//        }
//
//        return response.parse();

//        Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0")
//                    .timeout(10000)
//                    .get();
//        return doc;

        String html = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block ở đây nhưng không block trong HTTP IO

        return Jsoup.parse(html, url);
    }

    // Version với retry
    public Document fetchWithRetry(String url, Integer maxRetries) {
        int attempt = 0;
        Exception lastException = null;
        if (maxRetries == null) {
            maxRetries = 15;
        }

        while (attempt < maxRetries) {
            try {
                // Tăng delay giữa các retry
                if (attempt > 0) {
                    Thread.sleep(2000 * attempt);
                }

                return fetchWithCloudflareBypass(url);

            } catch (Exception e) {
                lastException = e;
                attempt++;
                log.debug("Attempt " + attempt + " failed: " + e.getMessage());
            }
        }
        log.error("Attempt " + attempt + " failed with url : {}", url);
        throw new RuntimeException("Failed after " + maxRetries + " attempts", lastException);
    }


    public static Document fetchWithSelenium(String url) {
        ChromeOptions options = new ChromeOptions();

        // ✅ Undetected Chrome options
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        // Headless mode (tùy chọn)
        // options.addArguments("--headless=new");

        // Loại bỏ các dấu hiệu automation
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);

            // Chờ Cloudflare challenge (5-10 giây)
            Thread.sleep(8000);

            // Lấy HTML sau khi Cloudflare đã verify
            String html = driver.getPageSource();

            // Parse với Jsoup
            return Jsoup.parse(html);

        } catch (Exception e) {
            throw new RuntimeException("Selenium failed", e);
        } finally {
            driver.quit();
        }
    }


    public static String downloadImageV2(HttpClient client, String imageUrl, String title,
                                         String chapterName, String mangaName) {
        String trace = String.valueOf(UUID.randomUUID());

        try {
            semaphore.acquire();

            log.info("Starting download: {}, uuid: {}", imageUrl, trace);

            // Validate URL
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                log.error("Invalid image URL, uuid: {}", trace);
                return null;
            }

            // Extract extension safely
            String ext = extractExtension(imageUrl);

            // Sanitize paths
            String mangaNameTitle = ConvertUtils.sanitizeFileName(mangaName);
            String sanitizeName = ConvertUtils.sanitizeFileName(title);
            Path path = Paths.get(coverImageDir, mangaNameTitle, chapterName, sanitizeName + ext);

            // Create directories
            Files.createDirectories(path.getParent());

            // Check if file already exists
            if (Files.exists(path) && Files.size(path) > 0) {
                log.info("File already exists, skipping: {}", path);
                return path.toString();
            }

            // Download with retry mechanism
            for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
                try {
                    // Random delay để tránh rate limit (tăng dần theo attempt)
                    int delay = calculateDelay(attempt);
                    Thread.sleep(delay);

                    log.info("Attempt {}/{} for: {}", attempt, MAX_RETRIES, imageUrl);

                    String result = downloadWithRetry(imageUrl, path, trace, attempt);

                    if (result != null) {
                        log.info("✓ Successfully downloaded: {}, uuid: {}", path, trace);
                        return result;
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Download interrupted: {}, uuid: {}", imageUrl, trace);
                    return null;

                } catch (IOException e) {
                    log.warn("Attempt {}/{} failed: {}, error: {}",
                            attempt, MAX_RETRIES, imageUrl, e.getMessage());

                    // Nếu chưa hết retry, tiếp tục
                    if (attempt < MAX_RETRIES) {
                        // Exponential backoff với jitter
                        int backoff = BASE_DELAY_MS * (int) Math.pow(2, attempt) + random.nextInt(1000);
                        log.info("Waiting {}ms before retry...", backoff);
                        Thread.sleep(backoff);
                    } else {
                        log.error("Failed after {} attempts: {}, uuid: {}",
                                MAX_RETRIES, imageUrl, trace);
                    }

                } catch (Exception e) {
                    log.error("Unexpected error on attempt {}: {}, uuid: {}",
                            attempt, imageUrl, trace, e);

                    if (attempt >= MAX_RETRIES) {
                        log.error("fail when retry attempt : {}, url :{}", attempt, imageUrl);
                        break;
                    }
                }
            }

            // Cleanup nếu download failed
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.warn("Failed to delete incomplete file: {}", path);
            }

            return null;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Semaphore interrupted: {}, uuid: {}", imageUrl, trace);
            return null;

        } catch (Exception e) {
            log.error("Fatal error downloading: {}, uuid: {}", imageUrl, trace, e);
            return null;

        } finally {
            semaphore.release();
        }
    }

    public static String downloadWithRetry(String imageUrl, Path path, String trace, int attempt)
            throws IOException, InterruptedException {

        // Tạo HttpClient MỚI cho mỗi request (bypass Cloudflare)
        HttpClient clientLocal = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        // Rotate User-Agent
        String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .timeout(Duration.ofSeconds(40)) // Tăng timeout
                .header("User-Agent", userAgent)
                .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Cache-Control", "no-cache")
                .header("Pragma", "no-cache")
                .header("Referer", "https://komik25.com/")
                .header("Sec-Fetch-Dest", "image")
                .header("Sec-Fetch-Mode", "no-cors")
                .header("Sec-Fetch-Site", "cross-site")
                .header("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                .header("Sec-Ch-Ua-Mobile", "?0")
                .header("Sec-Ch-Ua-Platform", "\"Windows\"")
                .GET()
                .build();

        HttpResponse<InputStream> response = null;

        try {
            response = clientLocal.send(request, HttpResponse.BodyHandlers.ofInputStream());

            int statusCode = response.statusCode();

            // Xử lý các status code khác nhau
            if (statusCode == 200) {
                return handleSuccessResponse(response, path, imageUrl, trace);

            } else if (statusCode == 403 || statusCode == 429) {
                // Cloudflare block hoặc rate limit
                log.warn("Cloudflare protection detected ({}): {}, attempt: {}",
                        statusCode, imageUrl, attempt);
                consumeResponseBody(response);

                // Delay lâu hơn cho Cloudflare
                Thread.sleep(3000 + random.nextInt(2000));
                throw new IOException("Cloudflare block: " + statusCode);

            } else if (statusCode >= 500 && statusCode < 600) {
                // Server error - retry
                log.warn("Server error ({}): {}, attempt: {}", statusCode, imageUrl, attempt);
                consumeResponseBody(response);
                throw new IOException("Server error: " + statusCode);

            } else if (statusCode == 404) {
                // Not found - không retry
                log.error("Image not found (404): {}", imageUrl);
                consumeResponseBody(response);
                return null;

            } else {
                // Other errors
                log.error("HTTP error ({}): {}", statusCode, imageUrl);
                consumeResponseBody(response);
                throw new IOException("HTTP error: " + statusCode);
            }

        } catch (IOException e) {
            // Network errors - có thể retry
            if (e.getMessage().contains("Connection reset") ||
                    e.getMessage().contains("Connection refused") ||
                    e.getMessage().contains("Broken pipe") ||
                    e.getMessage().contains("timeout")) {

                log.warn("Network error: {}, attempt: {}", e.getMessage(), attempt);
                throw e; // Retry
            }

            // Cleanup response nếu có
            if (response != null) {
                consumeResponseBody(response);
            }

            throw e;
        }
    }

    public static String handleSuccessResponse(HttpResponse<InputStream> response,
                                               Path path, String imageUrl, String trace)
            throws IOException {

        try (InputStream in = response.body();
             BufferedInputStream bis = new BufferedInputStream(in, 32768)) { // Buffer 32KB

            // Download vào temp file trước
            Path tempPath = Paths.get(path.toString() + ".tmp");

            long bytesCopied = Files.copy(bis, tempPath,
                    StandardCopyOption.REPLACE_EXISTING);

            if (bytesCopied == 0) {
                log.error("Downloaded file is empty: {}, uuid: {}", imageUrl, trace);
                Files.deleteIfExists(tempPath);
                throw new IOException("Empty file downloaded");
            }

            // Validate file size (ảnh thường > 1KB)
            if (bytesCopied < 1024) {
                log.warn("File too small ({}B), might be error page: {}", bytesCopied, imageUrl);

                // Đọc nội dung để check
                String content = Files.readString(tempPath);
                if (content.contains("<!DOCTYPE") || content.contains("<html")) {
                    log.error("Downloaded HTML instead of image: {}", imageUrl);
                    Files.deleteIfExists(tempPath);
                    throw new IOException("Invalid content type");
                }
            }

            // Move temp file to final destination
            Files.move(tempPath, path, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

            log.info("✓ Downloaded: {} ({} bytes), uuid: {}", path, bytesCopied, trace);
            return path.toString();
        }
    }

    public static void consumeResponseBody(HttpResponse<InputStream> response) {
        if (response != null) {
            try (InputStream in = response.body()) {
                // Đọc hết body để đóng connection đúng cách
                byte[] buffer = new byte[8192];
                while (in.read(buffer) != -1) {
                    // Discard data
                }
            } catch (IOException e) {
                // Ignore - đang cleanup
            }
        }
    }

    public static String extractExtension(String imageUrl) {
        try {
            // Remove query string
            int queryIndex = imageUrl.indexOf('?');
            String path = queryIndex > 0 ? imageUrl.substring(0, queryIndex) : imageUrl;

            int lastDot = path.lastIndexOf('.');
            int lastSlash = path.lastIndexOf('/');

            if (lastDot > lastSlash && lastDot < path.length() - 1) {
                String ext = path.substring(lastDot).toLowerCase();

                // Validate extension
                if (ext.matches("\\.(jpg|jpeg|png|gif|webp|svg|bmp|avif)")) {
                    return ext;
                }
            }
        } catch (Exception e) {
            log.warn("Cannot extract extension from: {}, using .jpg", imageUrl);
        }

        return ".jpg"; // Default
    }

    public static int calculateDelay(int attempt) {
        // Delay tăng dần:
        // Attempt 1: 500-1000ms
        // Attempt 2: 800-1500ms
        // Attempt 3+: 1000-2000ms
        int baseDelay = 500 + (attempt - 1) * 300;
        int randomDelay = random.nextInt(500);
        return baseDelay + randomDelay;
    }
}
