package com.example.comicbe.controller.craw;


import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.service.craw.MangaCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crawl")
public class MangaCrawController {
    @Autowired
    private MangaCrawlerService crawler;


    @GetMapping("/manga-list")
    public List<MangaDto> getMangaList() {
        return crawler.crawlMangaList();
    }

    @GetMapping("/manga-list2")
    public void getMangaList2() {
        crawler.crawlMangaList2(null);
    }

    @GetMapping("/chapters")
    public List<ChapterDto> getChapters(@RequestParam String url) {
        return crawler.crawlChapters(url);
    }

    @GetMapping("/images")
    public List<String> getImages(@RequestParam String url) {
        return crawler.crawlChapterImages(url);
    }
}
