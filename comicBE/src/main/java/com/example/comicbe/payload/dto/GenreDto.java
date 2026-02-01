package com.example.comicbe.payload.dto;

import com.example.comicbe.jpa.entity.MangaGenre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GenreDto implements Serializable {
    private Long id;
    private String name;

    @NotBlank
    private String code;

    private String slug;

    public GenreDto(MangaGenre mangaGenre){
        BeanUtils.copyProperties(mangaGenre, this);
    }
}
