package com.example.comicbe.utils.response;

import com.example.comicbe.utils.DateTimeUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author datdv
 */
@Getter
public class ResponseEntityBuilder {
    Map<String, Object> map = new HashMap<>();

    private ResponseEntityBuilder() {
        map.put("timestamp", DateTimeUtils.formatDateTimeNow());
        this.setCode(200);
    }

    public static ResponseEntityBuilder getBuilder() {
        return new ResponseEntityBuilder();
    }

    public ResponseEntityBuilder setCode(int code) {
        map.put("code", code);
        setSuccess(code < 400);
        return this;
    }


    public ResponseEntityBuilder setCode(HttpStatus code) {
        this.setCode(code.value());
        return this;
    }

    public ResponseEntityBuilder setMessage(String message) {
        map.put("message", message);
        return this;
    }

    public ResponseEntityBuilder set(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public ResponseEntityBuilder setSuccess(boolean isSuccess) {
        map.put("success", isSuccess);
        return this;
    }

    public ResponseEntityBuilder setData(Object data) {
        map.put("data", data);
        return this;
    }

    public ResponseEntity build() {
        if (map.get("message") == null && map.get("code") != null)
            map.put("message", HttpStatus.valueOf((Integer) map.get("code")).getReasonPhrase());
        int code = (Integer) map.get("code");
        return ResponseEntity.status(HttpStatus.valueOf(code))
                .body((map));
    }
}