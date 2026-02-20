package com.example.comicbe.controller.manga;

import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.payload.dto.GroupAlphabetDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PageRequestDTO;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.GenreMangaResponse;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.ViewService;
import com.example.comicbe.service.MangaService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ViewService viewService;

    @GetMapping
    public ResponseMessage mangaDtos() {
        return new ResponseMessage<>(mangaService.mangaDtos());

    }

    @GetMapping("/groupAlphabet")
    public ResponseMessage groupAlphabet() {
        Map<Character, List<MangaDto>> map = mangaService.groupFirstString();
        List<GroupAlphabetDto> groupAlphabetDtos = map.entrySet().stream().map(
                characterListEntry -> GroupAlphabetDto.builder()
                        .letter(String.valueOf(characterListEntry.getKey()))
                        .items(characterListEntry.getValue())
                        .build()).toList();
        return new ResponseMessage<>(groupAlphabetDtos);

    }

    @GetMapping("/retrieveWithParam")
    public ResponseMessage retriveMangas(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
//            @RequestParam(value = "directionType", required = false) String directionType,
            @RequestParam(value = "fieldSort", required = false) String fieldSort,
            @RequestParam(value = "genre_in", required = false) List<String> genre,
            @RequestParam(value = "genre_not", required = false) List<String> genreNot,
            @RequestParam(value = "status", required = false) List<String> status,
            @RequestParam(value = "pageNum", required = false) @Min(value = 1, message = "Page number must be larger than 0") final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) @Min(value = 1, message = "Page size must be larger than 0") final Integer pageSize
    ) {
        MangaFilter mangaFilter = MangaFilter.builder()
                .author(author)
                .category(category)
                .title(title)
                .genre(genre)
                .genreNotIn(genreNot)
                .status(status)
//                .direction(directionType)
                .fieldSort(fieldSort)
                .build();

        PageRequestDTO paging = PageRequestDTO.builder().pageNum(pageNum).pageSize(pageSize).build();
        return mangaService.fetchManga(new PagingFilterBase<>(mangaFilter, paging));

    }

    @GetMapping("/lastUpdate")
    public ResponseMessage lastUpdate(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "genre", required = false) List<String> genre,
            @RequestParam(value = "status", required = false) List<String> status,
            @RequestParam(value = "pageNum", required = false) @Min(value = 1, message = "Page number must be larger than 0") final Integer pageNum,
            @RequestParam(value = "pageSize", required = false) @Min(value = 1, message = "Page size must be larger than 0") final Integer pageSize
    ) {
        MangaFilter mangaFilter = MangaFilter.builder()
                .author(author)
                .category(category)
                .title(title)
                .build();

        PageRequestDTO paging = PageRequestDTO.builder().pageNum(pageNum).pageSize(pageSize).build();
        return mangaService.getLastUpdate(new PagingFilterBase<>(mangaFilter, paging));

    }

    @GetMapping("/{mangaId}")
    public ResponseMessage getMangaDetail(@PathVariable Long mangaId) {
        MangaDto mangaDto = mangaService.getDetailsById(mangaId);
        if (mangaDto != null) {
            mangaDto.setTotalView(viewService.fetchViewManga(mangaId));
            viewService.increaseViewV2(mangaId);
        }
        return new ResponseMessage<>(mangaDto);

    }

    @GetMapping("/suggest")
    public ResponseMessage suggest() {
        List<GenreMangaResponse> genreDtos = mangaService.getTop5MangaByGenres();
//        if (mangaDto != null) {
//            mangaDto.setTotalView(viewService.fetchViewManga(mangaId));
//            viewService.increaseViewV2(mangaId);
//        }
        return new ResponseMessage<>(genreDtos);

    }

//    @GetMapping("/suggest2")
//    public ResponseMessage suggest2() {
//        List<MangaDto> genreDtos = mangaService.getSuggest2();
////        if (mangaDto != null) {
////            mangaDto.setTotalView(viewService.fetchViewManga(mangaId));
////            viewService.increaseViewV2(mangaId);
////        }
//        return new ResponseMessage<>(genreDtos);
//
//    }

    @GetMapping("/popular")
    public ResponseMessage getMangaDetail() {
        Map<String, List<Long>> map = viewService.getPopularAll(0, 9);
        Map<String, List<MangaDto>> result = new HashMap<>();

        List<MangaDto> allManga = mangaService.mangaDtos();

        Map<Long, MangaDto> mangaMap = allManga.stream()
                .collect(Collectors.toMap(MangaDto::getId, m -> m));

        map.forEach((key, idList) -> {
            List<MangaDto> mangas = idList.stream()
                    .map(mangaMap::get)
                    .filter(Objects::nonNull)
                    .toList();

            result.put(key, mangas);
        });
        return new ResponseMessage<>(result);

    }

    @GetMapping("/rebuildView")
    public ResponseMessage rebuildView() {
        viewService.rebuildTotalView();
        return new ResponseMessage<>("SUCCESS");

    }

    @GetMapping("/pushView")
    public ResponseMessage pushView() {
        viewService.jobPushViewDaily();
        return new ResponseMessage<>("SUCCESS");

    }
}
