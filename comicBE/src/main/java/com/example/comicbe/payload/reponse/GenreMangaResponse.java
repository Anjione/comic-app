package com.example.comicbe.payload.reponse;

import com.example.comicbe.payload.dto.MangaDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreMangaResponse implements Serializable {
    private Long genreId;
    private String genreCode;
    private List<MangaDto> mangas = new ArrayList<>();
}
