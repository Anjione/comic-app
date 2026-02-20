package com.example.comicbe.payload.dto.mail;

import com.example.comicbe.constant.SendEmailType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagementSendEmailRequest implements Serializable {

    private List<String> toAddress;

    @NotBlank(groups = {Send.class}, message = "SEND_EMAIL_SUBJECT_NOT_EMPTY")
    private String subject;

    @NotBlank(groups = {Send.class}, message = "SEND_EMAIL_CONTENT_NOT_EMPTY")
    private String content;

    private SendEmailType type;

    public interface Send extends Default {
    }
}
