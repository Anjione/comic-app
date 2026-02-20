package com.example.comicbe.constant;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum FileType {
    IMAGE_GALLERY("GALLERY"), AUDIO("AUDIO"), PDF("PDF"), THUMBNAIL("THUMBNAIL"), AVATAR("AVATAR"), AVATAR_NEWS("AVATAR_NEWS");

    private final String code;

    private final static Map<String, FileType> lookup = new HashMap<>();

    static {
        EnumSet.allOf(FileType.class).forEach(item -> lookup.put(item.getCode(), item));
    }

    FileType(String code) {
        this.code = code;
    }

    public static FileType lookupForCode(String code) {
        return StringUtils.hasText(code) ? lookup.get(code) : null;
    }
}
