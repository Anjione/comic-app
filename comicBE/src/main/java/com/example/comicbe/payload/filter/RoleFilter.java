package com.example.comicbe.payload.filter;

import com.example.comicbe.jpa.spectification.base.SearchPageable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleFilter extends SearchPageable {
    private String nameValidator;
    private String name;

    private String roleText;
}
