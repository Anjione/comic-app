package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.MangaCategory;
import com.example.comicbe.jpa.entity.MangaGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MangaRepository extends JpaRepository<Manga, Long>, JpaSpecificationExecutor<Manga> {
    @Query("select c.id from Manga c")
    List<Long> findAllIds();

    @Query("select m.id, m.totalView from Manga m")
    List<Object[]> findAllViewCount();

    boolean existsByGenres(Set<MangaGenre> genres);

    boolean existsByCategory(MangaCategory category);

    @Query(value = """
            SELECT *
            FROM (
                SELECT
                    m.*,
                    mg.genre_id,
                    ROW_NUMBER() OVER (
                        PARTITION BY mg.genre_id
                        ORDER BY m.modified_date DESC
                    ) AS rn
                FROM manga m
                JOIN manga_genre mg ON mg.manga_id = m.id
                WHERE mg.genre_id IN (:genreIds)
            ) t
            WHERE t.rn <= :volume;
        """, nativeQuery = true)
    List<Manga> findLatest3ChaptersByMangaIds(
            @Param("genreIds") List<Long> genreIds,
            @Param("volume") Long volume
    );

    @Query(value = """
        SELECT DISTINCT t.id
        FROM (
            SELECT
                m.id,
                ROW_NUMBER() OVER (
                    PARTITION BY mg.genre_id
                    ORDER BY m.modified_date DESC
                ) AS rn
            FROM manga m
            JOIN manga_genre mg ON mg.manga_id = m.id
            WHERE mg.genre_id IN (:genreIds)
        ) t
        WHERE t.rn <= :volume """, nativeQuery = true)
    List<Long> findLatestMangaIdsByGenres(
            @Param("genreIds") List<Long> genreIds,
            @Param("volume") Long volume
    );


    @Query("""
        SELECT m
        FROM Manga m
        WHERE m.id IN :ids """)
    List<Manga> findAllByIdIn(@Param("ids") List<Long> ids);


    @Query(value = """
    SELECT
        g.id AS genreId,
        g.code AS genreCode,
        m.id AS mangaId,
        m.title AS mangaTitle,
        m.modified_date AS modifiedDate
    FROM (
        SELECT
            m.id AS manga_id,
            mg.genre_id,
            ROW_NUMBER() OVER (
                PARTITION BY mg.genre_id
                ORDER BY m.modified_date DESC
            ) AS rn
        FROM manga m
        JOIN manga_genre mg ON mg.manga_id = m.id
        WHERE mg.genre_id IN (:genreIds)
    ) t
    JOIN manga m ON m.id = t.manga_id
    JOIN genre g ON g.id = t.genre_id
    WHERE t.rn <= 5
    ORDER BY g.id, m.modified_date DESC
""", nativeQuery = true)
    List<MangaByGenreRow> findTop5MangaByGenres(
            @Param("genreIds") List<Long> genreIds
    );



}
