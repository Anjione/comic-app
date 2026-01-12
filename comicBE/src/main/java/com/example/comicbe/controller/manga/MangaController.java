package com.example.comicbe.controller.manga;

import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PageRequestDTO;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.ChapterViewServiceImpl;
import com.example.comicbe.service.MangaService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ChapterViewServiceImpl chapterViewService;

    @GetMapping
    public ResponseMessage mangaDtos() {
        return new ResponseMessage<>(mangaService.mangaDtos());

    }

    @GetMapping("/groupAlphabet")
    public ResponseMessage groupAlphabet() {
        return new ResponseMessage<>(mangaService.groupFirstString());

    }

    @GetMapping("/retrieveWithParam")
    public ResponseMessage retriveMangas(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "pageNum", required = false) @Min(value = 1, message = "Page number must be larger than 0") final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) @Min(value = 1, message = "Page size must be larger than 0") final Integer pageSize
    ) {
        MangaFilter mangaFilter = MangaFilter.builder()
                .author(author)
                .category(category)
                .title(title)
                .build();

        PageRequestDTO paging = PageRequestDTO.builder().pageNum(pageNum).pageSize(pageSize).build();
        return mangaService.fetchManga(new PagingFilterBase<>(mangaFilter, paging));

    }

    @GetMapping("/{mangaId}")
    public ResponseMessage getMangaDetail(@PathVariable Long mangaId) {
        MangaDto mangaDto = mangaService.getDetailsById(mangaId);
        if (mangaDto != null) {
            mangaDto.setTotalView(chapterViewService.fetchViewManga(mangaId));
            chapterViewService.increaseView(mangaId);
        }
        return new ResponseMessage<>(mangaDto);

    }
}
