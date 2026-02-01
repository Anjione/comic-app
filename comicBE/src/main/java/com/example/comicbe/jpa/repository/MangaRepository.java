package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.MangaCategory;
import com.example.comicbe.jpa.entity.MangaGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MangaRepository extends JpaRepository<Manga, Long>, JpaSpecificationExecutor<Manga> {
    @Query("select c.id from Manga c")
    List<Long> findAllIds();

    @Query("select m.id, m.totalView from Manga m")
    List<Object[]> findAllViewCount();

    boolean existsByGenres(Set<MangaGenre> genres);

    boolean existsByCategory(MangaCategory category);
}
