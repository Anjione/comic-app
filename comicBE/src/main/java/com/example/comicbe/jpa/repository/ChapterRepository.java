package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByManga(Manga manga);

    @Query(value = """
        SELECT *
        FROM (
            SELECT c.*,
                   ROW_NUMBER() OVER (
                       PARTITION BY c.manga_id
                       ORDER BY c.modified_date DESC
                   ) rn
            FROM chapter c
            WHERE c.manga_id IN (:mangaIds)
        ) t
        WHERE t.rn <= :volume
        """, nativeQuery = true)
    List<Chapter> findLatest3ChaptersByMangaIds(
            @Param("mangaIds") List<Long> mangaIds,
            @Param("volume") Long volume
    );
}
