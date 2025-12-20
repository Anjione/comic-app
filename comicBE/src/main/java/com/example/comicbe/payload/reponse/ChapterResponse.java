package com.example.comicbe.payload.reponse;

import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse implements Serializable {
    private String title;
    private String url;
    private String createdDate;


}
