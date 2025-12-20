package com.example.comicbe.service.manga;

import com.example.comicbe.entity.Manga;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.repository.MangaRepository;
import com.example.comicbe.service.MangaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaServiceImpl implements MangaService {
    @Autowired
    private MangaRepository mangaRepository;

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
        return mangas.stream().map(m -> {
            MangaDto mangaDto = new MangaDto();
            BeanUtils.copyProperties(m, mangaDto);
            return mangaDto;
        }).toList();
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
        }).toList();
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
