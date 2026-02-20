package com.example.comicbe.constant;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum CompareDate {
    EQUALS("eq"), BEFORE_DATE("bd"), AFTER_DATE("ad"), IN_HOURS("ih"), IN_DAY("id"), IN_WEEK("iwk"), IN_MONTH("im"), IN_YEAR("iyr");

    private final String code;

    private final static Map<String, CompareDate> lookup = new HashMap<>();

    static {
        EnumSet.allOf(CompareDate.class).forEach(item -> lookup.put(item.getCode(), item));
    }

    CompareDate(String code) {
        this.code = code;
    }

    public static CompareDate lookupForCode(String code) {
        return StringUtils.hasText(code) ? lookup.get(code) : null;
    }
}
