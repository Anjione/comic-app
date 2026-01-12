package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByManga(Manga manga);
}
