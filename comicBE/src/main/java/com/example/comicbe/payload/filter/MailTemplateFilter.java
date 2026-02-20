package com.example.comicbe.payload.filter;

import com.example.comicbe.jpa.spectification.base.SearchPageable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailTemplateFilter extends SearchPageable {
    private Long id;

    private String templateName;

    private String templateKey;

    private String templateCode;

    private Integer status;

    private String type;
}
