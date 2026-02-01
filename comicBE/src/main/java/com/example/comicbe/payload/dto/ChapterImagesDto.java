package com.example.comicbe.payload.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_CHAPTER_IMAGES")
public class ChapterImagesDto implements Serializable {
    private Integer imageNo;
    private String url;
    private String name;
    private String originUrl;
}
