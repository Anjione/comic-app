package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.MangaCategory;
import com.example.comicbe.jpa.entity.MangaGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<MangaGenre, Long> {
    List<MangaGenre> findAllByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

    @Query(value = """
            SELECT *
            FROM (
                SELECT
                    g.*,
                    ROW_NUMBER() OVER (
                        PARTITION BY mg.genre_id
                        ORDER BY m.modified_date DESC
                    ) AS rn
                    FROM manga m
                    JOIN manga_genre mg ON mg.manga_id = m.id
                    JOIN genre g on g.id = mg.genre_id
                WHERE mg.genre_id IN (:genreIds)
            ) t
            WHERE t.rn <= :volume;
        """, nativeQuery = true)
    List<MangaGenre> findLatest3ChaptersByMangaIds2(
            @Param("genreIds") List<Long> genreIds,
            @Param("volume") Long volume
    );
}
