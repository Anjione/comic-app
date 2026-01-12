package com.example.comicbe.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "upload_transaction")
@Getter
@Setter
@SequenceGenerator(name = BaseEntity.SEQ_DEFAULT, sequenceName = "SEQ_UPLOAD_TRANSACTION")
public class UploadTransaction extends BaseEntity{
    private String fileOriginName;
    private Integer importType; // 1 : import 1 chapter, 2 import multi chapter
    private String patch;
    private Integer staus; // 0 : processing, 1 : done
}
