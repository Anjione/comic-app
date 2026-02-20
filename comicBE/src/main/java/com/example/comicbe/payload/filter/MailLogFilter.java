package com.example.comicbe.payload.filter;

import com.example.comicbe.constant.EmailLogStatus;
import com.example.comicbe.jpa.spectification.base.SearchPageable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailLogFilter extends SearchPageable {

    private String emailTo;

    private LocalDateTime sentTime;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;

    private String typeQuery;

    private String subject;

    private String content;

    private EmailLogStatus status;

}
