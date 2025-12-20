package com.example.comicbe.service;

import java.util.List;
import java.util.Map;

public interface ChapterViewServiceImpl {
    void increaseView(Long mangaId);

    Map<Long, Long> fetchAllViews(List<Long> mangaIds);


    Long fetchViewManga(Long mangaId);

    void clearView(Long mangaId);
}
