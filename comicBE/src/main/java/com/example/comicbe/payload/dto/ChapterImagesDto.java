package com.example.comicbe.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_CHAPTER_IMAGES")
public class ChapterImagesDto implements Serializable {
    private Integer imageNo;
    private String url;
    private String name;
    private String originUrl;
}
