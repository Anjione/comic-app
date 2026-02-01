package com.example.comicbe.payload.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterDto implements Serializable {
    private Long id;
    private String title;
    private String chapterName;
    private String chapterUrl;
    private Double chapterNumber;

    List<ChapterImagesDto> images;
//    private String createdDate;
}
