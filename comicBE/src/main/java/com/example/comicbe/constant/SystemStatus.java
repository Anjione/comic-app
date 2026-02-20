package com.example.comicbe.constant;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum SystemStatus {
    INACTIVE(0), ACTIVE(1);

    private final Integer status;

    private final static Map<Integer, SystemStatus> lookup = new HashMap<>();

    static {
        EnumSet.allOf(SystemStatus.class).forEach(item -> lookup.put(item.getStatus(), item));
    }

    SystemStatus(Integer status) {
        this.status = status;
    }

    public static SystemStatus lookupForCode(Integer status) {
        return status != null ? lookup.get(status) : null;
    }
}
