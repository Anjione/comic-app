package com.example.comicbe.payload.filter;

import com.example.comicbe.jpa.spectification.base.SearchPageable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter extends SearchPageable {
    private String userLogged;
    private Long userId;
    private String email;
    private String username;
    private String fullName;
    private String role;
    private Boolean enabled;
    private Boolean queryPageable;

    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
