package com.example.comicbe.controller.manga;

import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.service.ChapterViewServiceImpl;
import com.example.comicbe.service.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ChapterViewServiceImpl chapterViewService;

    @GetMapping
    public List<MangaDto> mangaDtos() {
        return mangaService.mangaDtos();

    }

    @GetMapping("/{mangaId}")
    public MangaDto getMangaDetail(@PathVariable Long mangaId){
        MangaDto mangaDto =  mangaService.getDetailsById(mangaId);
        if (mangaDto != null){
            mangaDto.setTotalView(chapterViewService.fetchViewManga(mangaId));
            chapterViewService.increaseView(mangaId);
        }
        return mangaDto;

    }
}
