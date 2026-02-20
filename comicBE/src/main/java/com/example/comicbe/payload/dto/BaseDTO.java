package com.example.comicbe.payload.dto;

import com.example.comicbe.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseDTO implements Serializable {

    protected Long id;

    @JsonFormat(pattern = AppConstant.DATE.FORMAT_DATE_RESPONSE, timezone = AppConstant.DATE.TIMEZONE_ICT)
    @JsonIgnore
    private LocalDateTime createdDate;

    @JsonFormat(pattern = AppConstant.DATE.FORMAT_DATE_RESPONSE, timezone = AppConstant.DATE.TIMEZONE_ICT)
    @JsonIgnore
    private LocalDateTime modifiedDate;

    private String createdBy;
    private String modifiedBy;

}
