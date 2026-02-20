package com.example.comicbe.payload.dto.mail;

import com.example.comicbe.constant.EmailLogStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailLogDTO {

    private String emailTo;

    private LocalDateTime sentTime;

    private String subject;

    private String content;

    private EmailLogStatus status;
}
