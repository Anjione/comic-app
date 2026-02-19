package com.example.comicbe.service;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.payload.dto.GenreDto;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.GenreMangaResponse;
import com.example.comicbe.payload.reponse.ResponseMessage;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

public interface MangaService {
    List<MangaDto> mangaDtos();

    Map<Long, MangaDto> mangaMap();

    List<GenreDto> getSuggest();

    List<GenreMangaResponse> getTop5MangaByGenres();

    Map<Character, List<MangaDto>> groupFirstString();

    ResponseMessage fetchManga(PagingFilterBase<MangaFilter> mangaFilter);

    ResponseMessage getLastUpdate(PagingFilterBase<MangaFilter> mangaFilter);

    MangaDto getDetailsById(Long id);

    Manga insertWithCraw(Manga manga);
}
