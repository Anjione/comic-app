package com.example.comicbe.service.manga;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.MangaGenre;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.jpa.specs.MangaSpecs;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.MangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MangaServiceImpl implements MangaService {
    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaSpecs mangaSpecs;

    private void insert(MangaDto mangaDto) {
        Manga manga = new Manga();
        BeanUtils.copyProperties(mangaDto, manga);

    }

    @CacheEvict(value = "productCache", key = "#id")
    public void update(Long id, MangaDto mangaDto) {
        Manga manga = new Manga();
        BeanUtils.copyProperties(mangaDto, manga);

    }

    @Override
    @Cacheable(cacheNames = "mangaService.mangaDtos", keyGenerator = "keyGenerator")
    public List<MangaDto> mangaDtos() {
        List<Manga> mangas = mangaRepository.findAll();
        return mangas.stream()
                .sorted(Comparator.comparing(Manga::getTitle))
                .map(m -> {
                    MangaDto mangaDto = new MangaDto();
                    BeanUtils.copyProperties(m, mangaDto);
                    return mangaDto;
                })
                .toList();
    }

    @Override
    @Cacheable(cacheNames = "mangaService.groupFirstString", keyGenerator = "keyGenerator")
    public Map<Character, List<MangaDto>> groupFirstString() {
        List<MangaDto> mangaDtos = mangaDtos();
        Map<Character, List<MangaDto>> grouped =
                mangaDtos.stream()
                        .filter(i -> i.getTitle() != null && !i.getTitle().isBlank())
                        .collect(Collectors.groupingBy(
                                i -> Character.toUpperCase(i.getTitle().charAt(0))
                        ));
        return grouped;
    }

    @Override
    public ResponseMessage fetchManga(PagingFilterBase<MangaFilter> mangaFilter) {
        log.info("start retrive manga");
        Specification<Manga> mangaSpec = mangaSpecs.mangaFilterSpecification(mangaFilter.getFilter());
        List<Manga> mangas = new ArrayList<>();

        if (!mangaFilter.isPageable()) {
            log.info("get manga no pageable");
            mangas.addAll(mangaRepository.findAll(mangaSpec));
        } else {
            log.info("get manga with pageable");
            Page<Manga> mangasPage = mangaRepository.findAll(mangaSpec, PageRequest.of(mangaFilter.getPageNum(), mangaFilter.getPageSize()));
            mangaFilter.getPaging().setTotalRecords(mangasPage.getTotalElements());
            mangaFilter.getPaging().setTotalPages(mangasPage.getTotalPages());
            mangas = mangasPage.getContent();
        }

        List<MangaDto> respones = mangas.stream()
                .sorted(Comparator.comparing(Manga::getTitle))
                .map(m -> {
                    MangaDto mangaDto = new MangaDto();
                    BeanUtils.copyProperties(m, mangaDto);
                    mangaDto.setGenres(m.getGenres().stream().map(MangaGenre::getCode).toList());

                    return mangaDto;
                })
                .toList();

        return new ResponseMessage<>(respones, mangaFilter.getPaging());

    }

    @Override
    @Cacheable(value = "productCache", key = "#id")
    public MangaDto getDetailsById(Long id) {
        Manga manga = mangaRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        MangaDto mangaDto = new MangaDto();
        BeanUtils.copyProperties(manga, mangaDto);
        List<ChapterDto> chapterDtos = manga.getChapters().stream().map(chapter -> {
                    ChapterDto chapterDto = new ChapterDto();
                    BeanUtils.copyProperties(chapter, chapterDto);
//            chapterDto.getChapterName();
//            chapterDto.setTitle();
                    return chapterDto;
                })
                .sorted(Comparator.comparing(ChapterDto::getChapterNumber)).toList();
        mangaDto.setChapters(chapterDtos);
        return mangaDto;
    }

    public MangaDto findById(Long id) {
        Manga manga = mangaRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        MangaDto mangaDto = new MangaDto();
        BeanUtils.copyProperties(manga, mangaDto);
        List<ChapterDto> chapterDtos = manga.getChapters().stream().map(chapter -> {
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
//            chapterDto.getChapterName();
//            chapterDto.setTitle();
            return chapterDto;
        }).toList();
        mangaDto.setGenres(manga.getGenres().stream().map(MangaGenre::getCode).toList());
        mangaDto.setChapters(chapterDtos);
        return mangaDto;
    }

    @Override
//    @CachePut(value = "productCache", key = "#manga.id")
    public Manga insertWithCraw(Manga manga) {
        manga = mangaRepository.save(manga);
        return manga;
    }
}
