package com.example.comicbe.payload.dto;

import com.example.comicbe.constant.MangaCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MangaDto implements Serializable {
    private long id;
    private String title;
    private String url;
    private String mangaAvatarUrl;
    private String author;
    private Long totalView;
    private Double rating;
    private String createdDate;
    private String lastChapter;
    private String modifiedBy;
    private String createdBy;
    private MangaCategory mangaCategory;

    private List<ChapterDto> chapters;

}
