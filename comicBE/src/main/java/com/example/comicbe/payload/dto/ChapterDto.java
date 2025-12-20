package com.example.comicbe.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto implements Serializable {
    private String title;
    private String chapterName;
    private String chapterUrl;
    private String chapterNumber;
//    private String createdDate;
}
