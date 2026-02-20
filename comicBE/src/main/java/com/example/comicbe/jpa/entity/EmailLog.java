package com.example.comicbe.jpa.entity;

import com.example.comicbe.constant.EmailLogStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_log")
@Getter
@Setter
public class EmailLog extends BaseEntity {

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EmailLogStatus status;
}
