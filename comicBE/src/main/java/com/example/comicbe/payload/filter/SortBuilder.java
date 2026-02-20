package com.example.comicbe.payload.filter;

import com.example.comicbe.jpa.spectification.base.SearchPageable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortBuilder extends SearchPageable {

    private String sort;
    private String sortBy;
}
