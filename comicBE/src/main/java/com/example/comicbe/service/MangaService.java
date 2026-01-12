package com.example.comicbe.service;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.payload.dto.MangaDto;
import com.example.comicbe.payload.filter.MangaFilter;
import com.example.comicbe.payload.paging.PagingFilterBase;
import com.example.comicbe.payload.reponse.ResponseMessage;

import java.util.List;
import java.util.Map;

public interface MangaService {
    List<MangaDto> mangaDtos();

    Map<Character, List<MangaDto>> groupFirstString();

    ResponseMessage fetchManga(PagingFilterBase<MangaFilter> mangaFilter);

    MangaDto getDetailsById(Long id);

    Manga insertWithCraw(Manga manga);
}
