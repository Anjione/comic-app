package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.MangaCategory;
import com.example.comicbe.jpa.entity.MangaGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<MangaGenre, Long> {
    List<MangaGenre> findAllByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
