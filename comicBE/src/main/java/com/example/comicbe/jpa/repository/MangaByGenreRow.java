package com.example.comicbe.jpa.repository;

import java.time.LocalDateTime;

public interface MangaByGenreRow {
    Long getGenreId();
    String getGenreCode();
    Long getMangaId();
    String getMangaTitle();
    LocalDateTime getModifiedDate();
}
