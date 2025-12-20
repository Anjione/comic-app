package com.example.comicbe.controller.craw;


import com.example.comicbe.service.craw.ChapterCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crawl")
public class ChapterCrawController {
    @Autowired
    private ChapterCrawlerService crawler;


//    @GetMapping("/manga-list")
//    public List<MangaDto> getMangaList() {
//        return crawler.crawlMangaList();
//    }

    @GetMapping("/chapter-list2")
    public void getchapterList2() {
        String name = "a-quirky-girl-is-inviting-me-to-bed";
        crawler.crawlChapterList2(name, null, null);
    }

//    @GetMapping("/chapters")
//    public List<ChapterDto> getChapters(@RequestParam String url) {
//        return crawler.crawlChapters(url);
//    }
//
//    @GetMapping("/images")
//    public List<String> getImages(@RequestParam String url) {
//        return crawler.crawlChapterImages(url);
//    }
}
