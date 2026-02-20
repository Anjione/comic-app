package com.example.comicbe.payload.filter;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.LifecycleState;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true)
public class MangaFilter extends BaseFilter {
    private String author;
    private String title;
    private String category;
    private List<Long> genre;
    private List<Long> genreNotIn;
    private List<String> status;
    private String direction;
    private String fieldSort;

}
