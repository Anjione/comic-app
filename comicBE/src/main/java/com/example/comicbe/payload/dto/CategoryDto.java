package com.example.comicbe.payload.dto;

import com.example.comicbe.jpa.entity.MangaCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto implements Serializable {
    @NotNull(groups = {GenreDto.Update.class}, message = "id is not null")
    private Long id;

    private String name;

    @NotBlank(groups = {GenreDto.Insert.class}, message = "code is not null")
    private String code;
    private String slug;

    public CategoryDto(MangaCategory mangaCategory) {
        BeanUtils.copyProperties(mangaCategory, this);
    }

    public interface Insert extends Default {
    }

    public interface Update extends Default {
    }
}
