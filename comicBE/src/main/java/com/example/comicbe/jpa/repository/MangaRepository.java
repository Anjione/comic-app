package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MangaRepository extends JpaRepository<Manga, Long>, JpaSpecificationExecutor<Manga> {

}
