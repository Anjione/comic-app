package com.example.comicbe.service;

import com.example.comicbe.entity.Manga;
import com.example.comicbe.payload.dto.MangaDto;

import java.util.List;

public interface MangaService {
    List<MangaDto> mangaDtos();
    MangaDto getDetailsById(Long id);

    Manga insertWithCraw(Manga manga);
}
