package com.example.comicbe.payload.dto.mail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendMailRequest implements Serializable {

    @NotBlank(groups = {Send.class}, message = "SEND_EMAIL_TEMPLATE_CODE_NOT_EMPTY")
    private String templateCode;
    @NotBlank(groups = {Send.class}, message = "SEND_EMAIL_TO_ADDRESS_NOT_EMPTY")
    private String toAddress;
    @NotBlank(groups = {Send.class}, message = "SEND_EMAIL_SUBJECT_NOT_EMPTY")
    private String subject;

    private Map<String, String> bodyMap;

    public interface Send extends Default {
    }
}
