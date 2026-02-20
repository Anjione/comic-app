package com.example.comicbe.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


/**
 * @Author datdv
 */
@Getter
@Setter
public class AppException extends RuntimeException {

    private String code;

    private String message;

    private HttpStatus status;

    public AppException() {
        super();
    }

    public AppException(ErrorCode code) {
        super();
        this.code = code.code();
        this.message = code.message();
        this.status = code.status();
    }


}