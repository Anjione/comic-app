package com.example.comicbe.repository;

import com.example.comicbe.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {

}
