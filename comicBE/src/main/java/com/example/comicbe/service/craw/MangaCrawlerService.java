package com.example.comicbe.service.craw;

import com.example.comicbe.entity.Manga;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.service.MangaService;
import com.example.comicbe.utils.ConvertUtils;
import com.example.comicbe.utils.ToolDownload;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@Service
public class MangaCrawlerService {
    private String BASE_URL = "https://komik25.com";
        ExecutorService executorGL = Executors.newVirtualThreadPerTaskExecutor();
//    ExecutorService executorGL = Executors.newFixedThreadPool(30);
    private final Semaphore semaphore = new Semaphore(10);

    @Autowired
    @Qualifier("httpClient")
    private HttpClient httpClient;

    @Autowired
    private ChapterCrawlerService chapterCrawlerService;

    @Value("${crawler.cover-image-dir}")
    private String coverImageDir;

    @Autowired
    private MangaService mangaService;

@Autowired
private ToolDownload toolDownload;

    /**
     * Crawl danh sách truyện
     */
    public List<MangaDto> crawlMangaList() {
        List<MangaDto> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(BASE_URL + "/manga")
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements items = doc.select("div.page-item-detail.manga"); // Madara

            for (Element item : items) {
                String title = item.select("h3.h5 a").text();
                String url = item.select("h3.h5 a").attr("href");

//                list.add(new MangaDto(title, url));
            }
        } catch (Exception e) {
            log.error("Error crawling manga list", e);
        }

        return list;
    }

    /**
     * Crawl danh sách chapter của 1 truyện
     */
    public List<ChapterDto> crawlChapters(String mangaUrl) {
        List<ChapterDto> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(mangaUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements chaps = doc.select("li.wp-manga-chapter a"); // Madara

            for (Element c : chaps) {
                String title = c.text();
                String url = c.attr("href");
                ChapterDto chapterDto = new ChapterDto();
//                chapterDto.setTitle();
//                list.add(new ChapterDto(title, url));
            }

        } catch (IOException e) {
            log.error("Error crawling chapters", e);
        }

        return list;
    }

    /**
     * Crawl ảnh trong chapter
     */
    public List<String> crawlChapterImages(String chapterUrl) {
        List<String> images = new ArrayList<>();

        //            Document doc = Jsoup.connect(chapterUrl)
//                    .userAgent("Mozilla/5.0")
//                    .timeout(10000)
//                    .get();
        Document doc = toolDownload.fetchWithRetry(chapterUrl, null);
        Elements imgs = doc.select(".reading-content img");

        for (Element img : imgs) {
            images.add(img.attr("src"));
        }

        return images;
    }


//    public void crawlMangaListAsync(String page) {
//        executor.submit(() -> crawlMangaList2(page));
//    }

    public void crawlMangaList2(String page) {
        String defaultPage = BASE_URL + "/manga";
        String pageFind = defaultPage;
        if (StringUtils.hasText(page)) {
            pageFind = pageFind + page;
        }
//            Document doc = Jsoup.connect(pageFind)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120.0.0.0 Safari/537.36")
//                    .timeout(10000)
//                    .get();

        Document doc = toolDownload.fetchWithRetry(pageFind, null);


        Elements items = doc.select("div.listupd div.bsx > a");
        Elements nextpage = doc.select("div.hpage a.r");


        // THAY ĐỆ QUY BẰNG submit vào executor   temp cmt
        String next = nextpage.attr("href");
        if (StringUtils.hasText(next)) {
            log.info("Submit next page: {}", next);
            executorGL.submit(() -> crawlMangaList2(next));   // không block
        }

        log.info("Found {} manga items", items.size());

//            for (Element item : items) {
//                String url = item.attr("href");
//                String title = item.attr("title");
//                String imageUrl = item.select("img").attr("src");
//                String latestChapter = item.select("div.epxs").text();
//
//                // Kiểm tra tồn tại
////                if (mangaRepository.existsByUrl(url)) {
////                    log.info("Manga already exists: {}", title);
////                    continue;
////                }
//
//                // Tải ảnh
//                String localImage = downloadImage(imageUrl, title);
//
//                // Lưu vào DB
////                Manga manga = Manga.builder()
////                        .title(title)
////                        .url(url)
////                        .coverImage(localImage)
////                        .latestChapter(latestChapter)
////                        .build();
////
////                mangaRepository.save(manga);
//                log.info("Saved manga: {}", title);
//            }
        processItems(items);
//            String test = nextpage.attr("href");
//            if (StringUtils.hasText(nextpage.attr("href"))){
//                crawlMangaList2(test);
//            }

    }

    public void processItems(List<Element> items) {
        // Tạo executor với virtual threads (Java 21)
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (Element item : items) {
                executor.submit(() -> {
                    try {
                        String url = item.attr("href");
                        String title = item.attr("title");
                        String imageUrl = item.select("img").attr("src");
                        String latestChapter = item.select("div.epxs").text();

                        Double rating = Double.valueOf(item.select("div.numscore").text());


                        Manga manga = new Manga();
                        manga.setMangaAvatarUrl(imageUrl);
                        manga.setTitle(title);
                        manga.setLastChapter(latestChapter);
                        manga.setRating(rating);
                        manga.setMangaCode(UUID.randomUUID().toString());
                        // Tải ảnh
//                        String localImage = downloadImage(imageUrl, title);
                        manga = mangaService.insertWithCraw(manga);
                        Manga finalManga = manga;
                        executorGL.submit(() -> chapterCrawlerService.crawlChapterList2(title, url, finalManga));
//                        executorGL.submit(() -> mangaService.insertWithCraw(manga));


                        log.info("Saved manga: " + title);

                    } catch (Exception e) {
                        log.error("Error processing manga: " + e.getStackTrace());
                        log.error("Error processing manga: " + e.getCause());
                    }
                });
            }

            // Sau khi submit tất cả tasks, shutdown executor để chờ các task kết thúc
            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted: " + e.getMessage());
        }
    }

    private String downloadImage(String imageUrl, String title) {
        String trace = String.valueOf(UUID.randomUUID());
        try {
            semaphore.acquire();
            String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
//            String safeTitle = title.replaceAll("[^a-zA-Z0-9\\-]", "_");
            String safeTitle = ConvertUtils.sanitizeFileName(title);
            Path path = Paths.get(coverImageDir + "/" + safeTitle, safeTitle + ext);

//            try (InputStream in = new URL(imageUrl).openStream()) {
//                Files.createDirectories(path.getParent());
//                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            return path.toString();

//            HttpClient clientLocal = HttpClient.newBuilder()
//                    .followRedirects(HttpClient.Redirect.NORMAL)
//                    .connectTimeout(Duration.ofSeconds(10))
//                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .timeout(Duration.ofSeconds(20))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Referer", "https://komik25.com/")
                    .header("Sec-Fetch-Dest", "document")
                    .header("Sec-Fetch-Mode", "navigate")
                    .header("Sec-Fetch-Site", "none")
                    .GET()
                    .build();

            HttpResponse<InputStream> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (InputStream in = response.body()) {
                Files.createDirectories(path.getParent());
                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            }

            return path.toString();
        } catch (Exception e) {
            log.error("Failed to download image: {}, uuid : {} , e : {}", imageUrl, trace, e);
            return null;
        } finally {
            semaphore.release();
        }
    }



}
