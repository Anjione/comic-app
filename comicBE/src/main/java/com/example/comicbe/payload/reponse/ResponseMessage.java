package com.example.comicbe.payload.reponse;

import com.example.comicbe.payload.paging.PageRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseMessage<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date timestamp;
    private Integer status;
    private String error;
    private T data;
    private PageRequestDTO paging;
    private Object message;
    private String path;

    public ResponseMessage(Integer status, String error, Object message, String path) {
        super();
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ResponseMessage(T data) {
        super();
        this.status = HttpStatus.OK.value();
        this.timestamp = new Date();
        this.data = data;
    }

    public ResponseMessage(T data, PageRequestDTO paging) {
        super();
        this.status = HttpStatus.OK.value();
        this.timestamp = new Date();
        this.data = data;
        this.paging = paging;
    }

}
