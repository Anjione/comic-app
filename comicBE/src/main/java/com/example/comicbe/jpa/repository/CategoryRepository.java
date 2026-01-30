package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.MangaCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<MangaCategory, Long> {
    List<MangaCategory> findAllByCodeIgnoreCase(String code);
    Optional<MangaCategory> findByCodeIgnoreCase(String code);

}
