package com.example.comicbe.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum MangaCategory {
    MANGA(-1, ""),
    MANH_WA(0, ""),
    MANH_UA(1, ""),
    COMIC(2, ""),
    NOVEL(3, ""),
    ;

    private Integer type;
    private String description;

    // lookup hashMap
    private static Map<Integer, MangaCategory> lookup = new HashMap<>();

    static {
        EnumSet.allOf(MangaCategory.class).forEach(e -> lookup.put(e.getType(), e));
    }

    public static MangaCategory lookForType(Integer type) {
        return type != null ? lookup.get(type) : null;
    }

}
