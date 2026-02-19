package com.example.comicbe.payload.dto;

import com.example.comicbe.constant.MangaCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String mangaCategory;
    private List<String> genres;
    private Boolean colored;

    private List<ChapterDto> chapters;

}
