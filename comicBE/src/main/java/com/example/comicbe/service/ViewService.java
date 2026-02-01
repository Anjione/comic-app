package com.example.comicbe.service;

import com.example.comicbe.jpa.entity.Manga;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ViewService {
    void increaseView(Long mangaId);

    void increaseViewV2(Long mangaId);

    List<Long> listMangaPopular(String dataKey, long start, long end);

    Map<String, List<Long>> getPopularAll(long start, long end);

    Map<Long, Long> fetchAllViews(List<Long> mangaIds) throws ExecutionException, InterruptedException;


    Long fetchViewManga(Long mangaId);

    void clearView(Long mangaId);

    void rebuildTotalView();

    void syncView(Manga manga);
}
