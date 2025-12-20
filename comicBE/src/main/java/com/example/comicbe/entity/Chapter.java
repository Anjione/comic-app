package com.example.comicbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chapter")
@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_CHAPTER")
public class Chapter extends BaseEntity{
    private String chapterName;
    private String chapterCode;
    private String chapterNumber;

    @Column(columnDefinition = "longtext")
    private String chapterImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    private Manga manga;

//    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
//    private Set<ChapterAttribute> attributes;
}
