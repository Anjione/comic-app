package com.example.comicbe.service.chapter;

import com.example.comicbe.entity.Chapter;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.repository.ChapterRepository;
import com.example.comicbe.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements IChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

//    public List<ChapterDto> getChapterByMangaId(Long mangaId){
//        List<Chapter> chapters = chapterRepository.
//    }
}
