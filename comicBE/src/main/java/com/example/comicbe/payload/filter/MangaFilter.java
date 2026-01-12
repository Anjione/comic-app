package com.example.comicbe.payload.filter;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
public class MangaFilter extends BaseFilter {
    private String author;
    private String title;
    private String category;

}
