package com.example.comicbe.service.craw;

import com.example.comicbe.jpa.entity.*;
import com.example.comicbe.jpa.repository.CategoryRepository;
import com.example.comicbe.jpa.repository.ChapterRepository;
import com.example.comicbe.jpa.repository.GenreRepository;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.utils.ConvertUtils;
import com.example.comicbe.utils.ToolDownload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChapterCrawlerService {
    private String BASE_URL = "https://komik25.com/manga/";
    ExecutorService executorGL = Executors.newVirtualThreadPerTaskExecutor();
//    ExecutorService executorGL = Executors.newFixedThreadPool(20);

    private final Semaphore semaphore = new Semaphore(10);


    @Value("${crawler.cover-image-dir}")
    private String coverImageDir;

    @Value("${crawler.hotlink}")
    private boolean hotLink;

    @Autowired
    @Qualifier("httpClient")
    private HttpClient httpClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private ToolDownload toolDownload;

    /**
     * Crawl danh sách chapter của 1 truyện
     */
    public List<ChapterDto> crawlChapters(String mangaUrl) {
        List<ChapterDto> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(mangaUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120.0.0.0 Safari/537.36")
                    .timeout(10000)
                    .get();

            Elements chaps = doc.select("li.wp-manga-chapter a"); // Madara

            for (Element c : chaps) {
                String title = c.text();
                String url = c.attr("href");
//                list.add(new ChapterDto(title, url));
            }

        } catch (IOException e) {
            log.error("Error crawling chapters", e);
        }

        return list;
    }

//    /**
//     * Crawl ảnh trong chapter
//     */
//    public List<String> crawlChapterImages(String chapterUrl) {
//        List<String> images = new ArrayList<>();
//
//        try {
//            Document doc = Jsoup.connect(chapterUrl)
//                    .userAgent("Mozilla/5.0")
//                    .timeout(10000)
//                    .get();
//
//            Elements imgs = doc.select(".reading-content img");
//
//            for (Element img : imgs) {
//                images.add(img.attr("src"));
//            }
//
//        } catch (IOException e) {
//            log.error("Error crawling images", e);
//        }
//        return images;
//    }


//    public void crawlMangaListAsync(String page) {
//        executor.submit(() -> crawlMangaList2(page));
//    }

    public void crawlChapterList2(String mangaName, String url, Manga manga) {

        if (!StringUtils.hasText(url)) {
            String defaultPage = BASE_URL + mangaName;
            String pageFind = defaultPage;
            url = pageFind;
        }

        log.info("find list chapter with url : {}", url);
//            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0")
//                    .timeout(10000)
//                    .get();

        Document doc = toolDownload.fetchWithRetry(url, null);

        Elements chapters = doc.select("#chapterlist ul li");

        String type = doc
                .select("div.imptdt:contains(Type) a")
                .text().strip();

        String author = doc
                .select("div.imptdt:contains(Author) i")
                .text();

        String status = doc
                .select("div.imptdt:contains(Status) i")
                .text();

        Elements genres = doc.select("span.mgen a[rel=tag]");

        List<String> genreList = genres.eachText().stream().filter(Objects::nonNull)
                .filter(s -> StringUtils.hasText(s))
                .map(s -> s.toLowerCase().strip()).toList();

        List<MangaGenre> mangaGenres = genreRepository.findAll();
        Map<String, MangaGenre> stringMangaGenreMap = mangaGenres.stream().collect(Collectors.toMap(
                genre -> genre.getCode().toLowerCase().strip(),
                genre -> genre
        ));
        List<MangaGenre> mangaGenresManga = new ArrayList<>();
        List<MangaGenre> mangaGenresAdd = new ArrayList<>();
        genreList.stream().forEach(s -> {
            if (stringMangaGenreMap.containsKey(s)) {
                mangaGenresManga.add(stringMangaGenreMap.get(s));
            } else {
                MangaGenre mangaGenre1 = new MangaGenre();
                mangaGenre1.setCode(s);
                mangaGenresAdd.add(mangaGenre1);
            }
        });

        if (!CollectionUtils.isEmpty(mangaGenresAdd)) {
            log.info("insert genre with : {}" , mangaGenresAdd.size());
            mangaGenresManga.addAll(genreRepository.saveAll(mangaGenresAdd));
        }

        manga.setGenres(mangaGenresManga.stream().collect(Collectors.toSet()));

        Optional<MangaCategory> mangaCategory = categoryRepository.findByCodeIgnoreCase(type.strip());
        if (mangaCategory.isPresent()) {
            manga.setCategory(mangaCategory.get());
        } else {
            MangaCategory mangaCategoryNew = new MangaCategory();
            mangaCategoryNew.setCode(type.strip());
            log.info("insert category with code : {}", type);
            mangaCategoryNew = categoryRepository.save(mangaCategoryNew);
            manga.setCategory(mangaCategoryNew);
        }

        manga.setAuthor(author);
        mangaRepository.save(manga);
        processItemsWithList(chapters, mangaName, manga);

        // THAY ĐỆ QUY BẰNG submit vào executor
//            String next = nextpage.attr("href");
//            if (StringUtils.hasText(next)) {
//                log.info("Submit next page: {}", next);
//                executorGL.submit(() -> crawlMangaList2(next));   // không block
//            }
//
//            log.info("Found {} manga items", items.size());
//
//            processItems(items);

    }

    public void processItemsWithList(List<Element> items, String mangaName, Manga manga) {
        List<CompletableFuture<Chapter>> futures = new ArrayList<>();

        for (Element li : items) {

            CompletableFuture<Chapter> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Chapter chapter = new Chapter();

                    String dataNum = li.attr("data-num");
                    String url = li.select("a").attr("href");
                    String chapterTitle = li.select(".chapternum").text();
                    String chapterDate = li.select(".chapterdate").text();

                    chapter.setChapterCode(UUID.randomUUID().toString());
                    chapter.setChapterName(chapterTitle);
                    chapter.setChapterNumber(Double.valueOf(dataNum));
                    chapter.setManga(manga);

                    // Xử lý ảnh trong chapter
                    List<ChapterImages> imgs =
                            processImagesInChapter(url, chapterTitle, mangaName);

                    chapter.setChapterImages(mapper.writeValueAsString(imgs));

                    return chapter;

                } catch (Exception e) {
                    log.error("Error processing chapter: {}", e.getMessage());
                    return null; // hoặc throw RuntimeException nếu muốn fail-fast
                }
            }, executorGL);

            futures.add(future);
        }

        List<Chapter> chapters = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .sorted((c1, c2) -> {
//                    String s1 = c1.getChapterNumber();
//                    String s2 = c2.getChapterNumber();
//
//                    boolean isNum1 = s1.matches("\\d+");
//                    boolean isNum2 = s2.matches("\\d+");

                    // cả hai là số
//                    if (isNum1 && isNum2) {
//                        return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
//                    }

//                    // số đứng trước chữ
//                    if (isNum1) return -1;
//                    if (isNum2) return 1;
//
//                    // cả hai là chữ
//                    return s1.compareToIgnoreCase(s2);
                    return Double.compare(c1.getChapterNumber(), c2.getChapterNumber());
                })
                .toList();

        chapterRepository.saveAll(chapters);
    }

    public void processItems(List<Element> items, String mangaName, Manga manga) {
        // Tạo executor với virtual threads (Java 21)
//        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

        for (Element li : items) {
            executorGL.submit(() -> {
                try {

                    Chapter chapter = new Chapter();
//                         Lấy số chapter từ attribute
                    String dataNum = li.attr("data-num");

                    // Lấy link
                    String url = li.select("a").attr("href");

                    // Lấy tên hiển thị - "Chapter 25"
                    String chapterTitle = li.select(".chapternum").text();

                    // Lấy ngày cập nhật
                    String chapterDate = li.select(".chapterdate").text();

                    chapter.setChapterCode(UUID.randomUUID().toString());
                    chapter.setChapterName(chapterTitle);
                    chapter.setChapterNumber(Double.valueOf(dataNum));

                    List<ChapterImages> chapterImages = this.processImagesInChapter(url, chapterTitle, mangaName);
                    chapter.setChapterImages(mapper.writeValueAsString(chapterImages));
                    chapter.setManga(manga);
                    chapterRepository.save(chapter);
                    // Tải ảnh
//                        String localImage = downloadImage(url, title);
//
//
//                        log.info("Saved manga: " + title);

                } catch (Exception e) {
                    log.error("Error processing manga: " + e.getMessage());
                }
            });
        }

        // Sau khi submit tất cả tasks, shutdown executor để chờ các task kết thúc
//            executor.shutdown();
//            while (!executor.isTerminated()) {
//                Thread.sleep(100);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            log.error("Thread interrupted: " + e.getMessage());
//        }
    }


    private List<ChapterImages> processImagesInChapter(String url, String chapterName, String mangaName) throws IOException {
        Document doc = toolDownload.fetchWithRetry(url, null);
        Elements imgs = doc.select("#readerarea img");

        List<String> realImages = new ArrayList<>();
//        HttpClient client = HttpClient.newBuilder()
//                .connectTimeout(Duration.ofSeconds(100))
//                .followRedirects(HttpClient.Redirect.NORMAL)
//                .version(HttpClient.Version.HTTP_2)   // nhanh hơn
//                .build();

        List<ChapterImages> chapterImages = new ArrayList<>();


        for (Element img : imgs) {
//            String src = img.attr("src");
//            String alt = img.attr("alt");

            String src = img.attr("src");
            ChapterImages chapterImagesDetail = new ChapterImages();

            chapterImagesDetail.setImageNo(imgs.indexOf(img));
            chapterImagesDetail.setName("");
            chapterImagesDetail.setOriginUrl(src);
            chapterImagesDetail.setUrl(src);
            chapterImages.add(chapterImagesDetail);

//            String dataSrc = img.attr("data-src");

//            String realUrl;
//
//
//            // Nếu src là ảnh thật
//            if (src.contains("img.komik25.com")) {
//                realUrl = src;
//            }
            // Nếu src là placeholder nhưng data-src có ảnh thật
//            else if (dataSrc.contains("img.komik25.com")) {
//                realUrl = dataSrc;
//            } else {
//                continue; // bỏ ảnh placeholder
//            }


//            if (!StringUtils.hasText(alt)) {
//                alt = realUrl;
//                String test = "https://img.komik25.com/" + mangaName + "/" + chapterName;
//                alt = alt.replace("https://img.komik25.com/" + mangaName + "/" + chapterName, "");
//            }
//            this.downloadImage(realUrl, alt, chapterName, mangaName);
//            String finalAlt = alt;
            if (hotLink) {
                URL urlImage = new URL(src);
                String path = urlImage.getPath();
                String filename = Paths.get(path).getFileName().toString();
                executorGL.submit(() -> ToolDownload.downloadImageV2(httpClient, src, filename, chapterName, mangaName));
            }


//            // Nếu src là ảnh thật
//            if (src.contains("img.komik25.com")) {
//                realUrl = src;
//            }
//            // Nếu src là placeholder nhưng data-src có ảnh thật
//            else if (dataSrc.contains("img.komik25.com")) {
//                realUrl = dataSrc;
//            }
//            else {
//                continue; // bỏ ảnh placeholder
//            }
//
//            realImages.add(realUrl);
        }

        return chapterImages;

//        realImages.forEach(System.out::println);
    }

//    private String downloadImage(String imageUrl, String title, String chapterName, String mangaName) {
//        try {
//            log.info("imageUrl : {}", imageUrl);
//            String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
//            String safeTitle = title.replaceAll("[^a-zA-Z0-9\\-]", "_");
//            Path path = Paths.get(coverImageDir + "/" + mangaName + "/" + chapterName, safeTitle + ext);
//
//            try (InputStream in = new URL(imageUrl).openStream()) {
//                Files.createDirectories(path.getParent());
//                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
//            }
//
//            return path.toString();
//        } catch (Exception e) {
//            log.error("Failed to download image: " + imageUrl, e);
//            return null;
//        }
//    }


    private String downloadImageV2(HttpClient client, String imageUrl, String title, String chapterName, String mangaName) {
        String trace = String.valueOf(UUID.randomUUID());
        try {
            semaphore.acquire();

            semaphore.acquire();
            log.info("imageUrl : {}", imageUrl);
            String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
//            String safeTitle = title.replaceAll("[^a-zA-Z0-9\\-]", "_");
//            String safeTitle = chapterName + UUID.randomUUID();
            String mangaNameTitle = ConvertUtils.sanitizeFileName(mangaName);
            String sanitizeName = ConvertUtils.sanitizeFileName(title);
            Path path = Paths.get(coverImageDir + "/" + mangaNameTitle + "/" + chapterName, sanitizeName + ext);

            Files.createDirectories(path.getParent());

            HttpClient clientLocal = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .timeout(Duration.ofSeconds(30))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120.0.0.0 Safari/537.36")
                    .header("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Referer", "https://komik25.com/")
                    .header("Sec-Fetch-Dest", "image")
                    .header("Sec-Fetch-Mode", "no-cors")
                    .GET()
                    .build();

            HttpResponse<InputStream> response = clientLocal.send(
                    request,
                    HttpResponse.BodyHandlers.ofInputStream()
            );

            if (response.statusCode() != 200) {
                log.error("Failed to download image: {}, HTTP {}, uuid: {}",
                        imageUrl, response.statusCode(), trace);

                try (InputStream in = response.body()) {
                    in.readAllBytes(); // Đọc và bỏ qua
                }
                return null;
            }

            try (InputStream in = response.body();
                 BufferedInputStream bis = new BufferedInputStream(in, 16384)) {

                // Files.copy tự động đọc hết InputStream
                long bytesCopied = Files.copy(bis, path,
                        StandardCopyOption.REPLACE_EXISTING);

                if (bytesCopied == 0) {
                    log.error("Downloaded file is empty: {}, uuid: {}", imageUrl, trace);
                    Files.deleteIfExists(path);
                    return null;
                }

                log.info("✓ Downloaded: {} ({} bytes), uuid: {}", path, bytesCopied, trace);
            }

            return path.toString();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Download interrupted: {}, uuid: {}", imageUrl, trace, e);
            return null;
        } catch (IOException e) {
            log.error("Failed to download image: {}, uuid: {}, error: {}",
                    imageUrl, trace, e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error downloading image: {}, uuid: {}",
                    imageUrl, trace, e);
            return null;
        } finally {
            semaphore.release();
        }
    }


    private String downloadImage(HttpClient client, String imageUrl, String title, String chapterName, String mangaName) {
        try {

            semaphore.acquire();
            log.info("imageUrl : {}", imageUrl);
            String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
//            String safeTitle = title.replaceAll("[^a-zA-Z0-9\\-]", "_");
//            String safeTitle = chapterName + UUID.randomUUID();
            String mangaNameTitle = ConvertUtils.sanitizeFileName(mangaName);
            String sanitizeName = ConvertUtils.sanitizeFileName(title);
            Path path = Paths.get(coverImageDir + "/" + mangaNameTitle + "/" + chapterName, sanitizeName + ext);

//            HttpClient clientLocal = HttpClient.newBuilder()
//                    .followRedirects(HttpClient.Redirect.NORMAL)
//                    .connectTimeout(Duration.ofSeconds(2000))
//                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .timeout(Duration.ofSeconds(2000))
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
            log.error("Failed to download image: " + imageUrl, e);
            return null;
        } finally {
            semaphore.release();
        }
    }
}
