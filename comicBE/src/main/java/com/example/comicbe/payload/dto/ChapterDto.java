package com.example.comicbe.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto implements Serializable {
    private Long id;
    private String title;
    private String chapterName;
    private String chapterUrl;
    private Double chapterNumber;

    List<ChapterImagesDto> images;
//    private String createdDate;
}
