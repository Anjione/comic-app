package com.example.comicbe.payload.dto.mail;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MailTemplateRequest {
    private Long id;
    @NotBlank
    private String templateName;
    @NotBlank
    private String templateCode;
    @NotBlank
    private String templateKey;
    @NotBlank
    private String content;
    private String type;
}
