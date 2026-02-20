package com.example.comicbe.service.manga;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.MangaGenre;
import com.example.comicbe.jpa.repository.ChapterRepository;
import com.example.comicbe.jpa.repository.GenreRepository;
import com.example.comicbe.jpa.repository.MangaByGenreRow;
import com.example.comicbe.jpa.repository.MangaRepository;
import com.example.comicbe.jpa.spectification.specs.MangaSpecs;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.GenreMangaResponse;
import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.GenreService;
import com.example.comicbe.service.MangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MangaServiceImpl implements MangaService {
    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private MangaSpecs mangaSpecs;
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

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


    @Cacheable(cacheNames = "mangaService.mangaMap", keyGenerator = "keyGenerator")
    @Override
    public Map<Long, MangaDto> mangaMap() {
        List<Manga> mangas = mangaRepository.findAll();
        return mangas.stream()
                .sorted(Comparator.comparing(Manga::getTitle))
                .map(m -> {
                    MangaDto mangaDto = new MangaDto();
                    BeanUtils.copyProperties(m, mangaDto);
                    return mangaDto;
                })
                .collect(Collectors.toMap(MangaDto::getId, mangaDto -> mangaDto));
    }

    @Override
    public List<GenreDto> getSuggest() {
        Long now = Instant.now().toEpochMilli();
        log.info("1: {}", (Instant.now().toEpochMilli() - now));
        List<GenreDto> genreDtos = genreService.fetchAll();
        List<GenreDto> random = new ArrayList<>();
        if (genreDtos.size() > 5) {
            random = new ArrayList<>(genreDtos.subList(0, 5));
        } else {
            random = genreDtos;
        }

        log.info("2: {}", Instant.now().toEpochMilli() - now);

        List<MangaGenre> mangaGenres = genreRepository.findLatest3ChaptersByMangaIds2(random.stream().map(GenreDto::getId).toList(), 1L);
        log.info("3: {}", Instant.now().toEpochMilli() - now);
        List<GenreDto> genreDtos1 = mangaGenres.stream().map(mangaGenre -> {
            GenreDto genreDto = new GenreDto(mangaGenre);
            List<MangaDto> mangas = mangaGenre.getMangas().stream()
                    .sorted(Comparator.comparing(Manga::getTitle))
                    .map(m -> {
                        MangaDto mangaDto = new MangaDto();
                        BeanUtils.copyProperties(m, mangaDto);
                        return mangaDto;
                    })
                    .toList();
            genreDto.setMangas(mangas);
            return genreDto;
        }).toList();
        log.info("4: {}", Instant.now().toEpochMilli() - now);
        return genreDtos1;

    }

    @Override
    public List<GenreMangaResponse> getTop5MangaByGenres() {
        List<GenreDto> genreDtos = genreService.fetchAll();
        List<GenreDto> random = new ArrayList<>();
        if (genreDtos.size() > 5) {
            random = new ArrayList<>(genreDtos.subList(0, 5));
        } else {
            random = genreDtos;
        }
        List<MangaByGenreRow> rows =
                mangaRepository.findTop5MangaByGenres(random.stream().map(GenreDto::getId).toList());

        Map<Long, GenreMangaResponse> map = new LinkedHashMap<>();
        Map<Long, MangaDto> mapManga = mangaMap();

        for (MangaByGenreRow row : rows) {
            GenreMangaResponse response = map.computeIfAbsent(
                    row.getGenreId(),
                    id -> {
                        GenreMangaResponse g = new GenreMangaResponse();
                        g.setGenreId(row.getGenreId());
                        g.setGenreCode(row.getGenreCode());
                        return g;
                    }
            );
            if (mapManga.containsKey(row.getMangaId())) {
                response.getMangas().add(
                        mapManga.get(row.getMangaId())
                );
            }


        }

        return new ArrayList<>(map.values());
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

            Sort sort = Optional.ofNullable(mangaFilter.getFilter())
                    .map(f -> f.getFieldSort())
                    .map(s -> {
                        String[] parts = s.split(",");

                        String field = parts[0].trim();
                        Sort.Direction direction = Sort.Direction.ASC;

                        if (parts.length > 1) {
                            try {
                                direction = Sort.Direction.fromString(parts[1].trim());
                            } catch (IllegalArgumentException e) {
                                direction = Sort.Direction.ASC;
                            }
                        }

                        return Sort.by(direction, field);
                    })
                    .orElse(Sort.by(Sort.Direction.ASC, "id"));
            mangas.addAll(mangaRepository.findAll(mangaSpec, sort));
        } else {
            log.info("get manga with pageable");
            Sort sort = Optional.ofNullable(mangaFilter.getFilter())
                    .map(f -> f.getFieldSort())
                    .map(s -> {
                        String[] parts = s.split(",");

                        String field = parts[0].trim();
                        Sort.Direction direction = Sort.Direction.ASC;

                        if (parts.length > 1) {
                            try {
                                direction = Sort.Direction.fromString(parts[1].trim());
                            } catch (IllegalArgumentException e) {
                                direction = Sort.Direction.ASC;
                            }
                        }

                        return Sort.by(direction, field);
                    })
                    .orElse(Sort.by(Sort.Direction.ASC, "id"));

            Page<Manga> mangasPage = mangaRepository.findAll(mangaSpec, PageRequest.of(mangaFilter.getPageNum(), mangaFilter.getPageSize(), sort));
            mangaFilter.getPaging().setTotalRecords(mangasPage.getTotalElements());
            mangaFilter.getPaging().setTotalPages(mangasPage.getTotalPages());
            mangas = mangasPage.getContent();
        }

        List<MangaDto> respones = mangas.stream()
//                .sorted(Comparator.comparing(Manga::getTitle))
                .map(m -> {
                    MangaDto mangaDto = new MangaDto();
                    BeanUtils.copyProperties(m, mangaDto);
//                    mangaDto.setGenres(m.getGenres().stream().map(MangaGenre::getCode).toList());
                    if (Objects.nonNull(m.getCategory())){
                        mangaDto.setMangaCategory(m.getCategory().getCode());

                    }

                    return mangaDto;
                })
                .toList();

        return new ResponseMessage<>(respones, mangaFilter.getPaging());

    }

    @Override
    public ResponseMessage getLastUpdate(PagingFilterBase<MangaFilter> mangaFilter) {
        log.info("start retrive manga");
        Specification<Manga> mangaSpec = mangaSpecs.mangaFilterSpecification(mangaFilter.getFilter());
        List<Manga> mangas = new ArrayList<>();

        if (!mangaFilter.isPageable()) {
            log.info("get manga no pageable");
            mangas.addAll(mangaRepository.findAll(mangaSpec, Sort.by(Sort.Direction.DESC, "modifiedDate")));
        } else {
            log.info("get manga with pageable");
            Page<Manga> mangasPage = mangaRepository.findAll(mangaSpec, PageRequest.of(mangaFilter.getPageNum(), mangaFilter.getPageSize(), Sort.by(Sort.Direction.DESC, "modifiedBy")));
            mangaFilter.getPaging().setTotalRecords(mangasPage.getTotalElements());
            mangaFilter.getPaging().setTotalPages(mangasPage.getTotalPages());
            mangas = mangasPage.getContent();
        }
        List<Long> ids = mangas.stream().map(Manga::getId).collect(Collectors.toSet()).stream().toList();

        List<Chapter> chapters = chapterRepository.findLatest3ChaptersByMangaIds(ids, 3L);
        Map<Long, List<ChapterDto>> chapterMap = chapters.stream()
                .map(chapter -> {
                    ChapterDto dto = new ChapterDto();
                    dto.setMangaId(chapter.getManga().getId());
                    BeanUtils.copyProperties(chapter, dto, "images");
                    return dto;
                })
                .collect(Collectors.groupingBy(ChapterDto::getMangaId));

        List<MangaDto> respones = mangas.stream()
                .sorted(Comparator.comparing(Manga::getTitle))
                .map(m -> {
                    MangaDto mangaDto = new MangaDto();
                    BeanUtils.copyProperties(m, mangaDto);
//                    mangaDto.setGenres(m.getGenres().stream().map(MangaGenre::getCode).toList());
//                    mangaDto.setMangaCategory(m.getCategory().getCode());
                    if (chapterMap.containsKey(m.getId())) {
                        mangaDto.setChapters(chapterMap.get(m.getId()));
                    }
                    if (Objects.nonNull(m.getCategory())) {
                        mangaDto.setMangaCategory(m.getCategory().getCode());
                    }

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
        mangaDto.setGenres(manga.getGenres().stream().map(MangaGenre::getCode).toList());
        if (Objects.nonNull(manga.getCategory())) {
            mangaDto.setMangaCategory(manga.getCategory().getCode());
        }
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
