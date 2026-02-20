package com.example.comicbe.payload.dto.mail;

import com.example.comicbe.payload.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailTemplateDTO extends BaseDTO {
    private String templateName;

    private String templateCode;

    private String templateKey;

    private String content;

    private Integer status;
    private Boolean isConfig;
    private String type;
}
