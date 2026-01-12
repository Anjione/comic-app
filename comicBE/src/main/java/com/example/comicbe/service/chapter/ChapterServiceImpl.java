package com.example.comicbe.service.chapter;

import com.example.comicbe.jpa.entity.Chapter;
import com.example.comicbe.jpa.entity.ChapterImages;
import com.example.comicbe.jpa.repository.ChapterRepository;
import com.example.comicbe.payload.dto.ChapterDto;
import com.example.comicbe.payload.dto.ChapterImagesDto;
import com.example.comicbe.service.IChapterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ChapterServiceImpl implements IChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    public List<ChapterDto> getChapterByMangaId(Long mangaId){
//        List<Chapter> chapters = chapterRepository.findAllByManga()
//    }

    @Override
    public ChapterDto chapterDetail(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(() -> new RuntimeException("id not found"));
        ChapterDto chapterDto = new ChapterDto();
        BeanUtils.copyProperties(chapter, chapterDto);
        try {
            List<ChapterImages> chapterImages = objectMapper.readValue(chapter.getChapterImages(), new TypeReference<>() {
            });
            List<ChapterImagesDto> chapterImagesDtos = chapterImages.stream()
                    .sorted(Comparator.comparingInt(ChapterImages::getImageNo))
                    .map(c -> {
                        ChapterImagesDto chapterImagesDto = new ChapterImagesDto();
                        BeanUtils.copyProperties(c, chapterImagesDto);
                        return chapterImagesDto;
                    })
                    .toList();
            chapterDto.setImages(chapterImagesDtos);
        } catch (JsonProcessingException e) {
            log.error("convert images error");
        }

        return chapterDto;
    }
}
