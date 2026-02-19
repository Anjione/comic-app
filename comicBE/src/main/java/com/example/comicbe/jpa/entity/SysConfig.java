package com.example.comicbe.jpa.entity;

import com.example.comicbe.constant.SysConfigCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_config")
@Getter
@Setter
public class SysConfig extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private SysConfigCode code;

    @Column(name = "value")
    private String value;
}
