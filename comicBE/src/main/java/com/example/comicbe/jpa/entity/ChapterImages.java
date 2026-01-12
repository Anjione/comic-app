package com.example.comicbe.jpa.entity;

import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_CHAPTER_IMAGES")
public class ChapterImages implements Serializable {
    private Integer imageNo;
    private String url;
    private String name;
    private String originUrl;
}
