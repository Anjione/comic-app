package com.example.comicbe.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Table(name = "category")
@Getter
@Setter
@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_MANGA_CATEGORY")
public class MangaCategory extends BaseEntity{
    private String name;
    private String code;
    private String slug ;

    @OneToMany(mappedBy = "category")
    private List<Manga> mangas;
}
