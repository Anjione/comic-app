package com.example.comicbe.service;

import com.example.comicbe.payload.dto.ChapterDto;

public interface IChapterService {
    ChapterDto chapterDetail(long chapterId);
}
