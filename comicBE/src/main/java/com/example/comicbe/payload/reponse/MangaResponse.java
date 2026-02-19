package com.example.comicbe.payload.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MangaResponse implements Serializable {
    private String title;
    private String author;
    private Long totalView;
    private String createdDate;
    private String modifiedBy;
    private String createdBy;
}
