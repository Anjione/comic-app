package com.example.comicbe.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "manga")
@Getter
@Setter
@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_MANGA")
public class Manga extends BaseEntity{
    private String mangaName;
    private String mangaCode;
    private String mangaAvatarUrl;
    private String title;
    private String lastChapter; // temp

    private String author;
    private Long totalView;
    private Double rating;
    private Boolean colored;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MangaCategory category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "manga_genre",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<MangaGenre> genres;

    @OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Chapter> chapters;
}
