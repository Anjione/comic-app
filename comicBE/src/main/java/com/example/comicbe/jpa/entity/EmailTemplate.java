package com.example.comicbe.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email_template")
@Getter
@Setter
public class EmailTemplate extends BaseEntity {

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_code")
    private String templateCode;

    @Column(name = "template_key")
    private String templateKey;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_config")
    private Boolean isConfig;

    @Column(name = "type")
    private String type;
}
