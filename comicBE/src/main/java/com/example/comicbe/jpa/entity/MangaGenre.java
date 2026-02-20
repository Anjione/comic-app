package com.example.comicbe.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "genre")
@Getter
@Setter
@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_GENRE")
public class MangaGenre extends BaseEntity {

    private String name;
    private String code;

    private String slug;

    @OneToMany(mappedBy = "genre")
    Set<MangaGenreMapping> genreMappings;
}
