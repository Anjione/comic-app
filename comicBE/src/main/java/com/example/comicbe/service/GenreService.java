package com.example.comicbe.service;

import com.example.comicbe.payload.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> fetchAll();

    GenreDto insert(GenreDto genreDto);

    GenreDto update(GenreDto genreDto);

    void delete(Long id);
}
