package com.example.comicbe.controller.chapter;

import com.example.comicbe.payload.reponse.ResponseMessage;
import com.example.comicbe.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chapter")
public class ChapterController {
    @Autowired
    private IChapterService iChapterService;

    @GetMapping("/{id}")
    public ResponseMessage fetchChapterDetail(
            @PathVariable long id) {
        return new ResponseMessage<>(iChapterService.chapterDetail(id));
    }

}
