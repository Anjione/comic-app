package com.example.comicbe.utils;

import java.lang.reflect.Field;

public class ConvertUtils {

    private static final String os = System.getProperty("os.name").toLowerCase();
    public static String sanitizeFileName(String name) {

        if (os.contains("win")) {
            return name.replaceAll("[\\\\/:*?\"<>|]", "_");
        }
        return name.replace("/", "_");
    }

    public static void trimAllStringFields(Object obj) {
        if (obj == null) return;

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(obj);
                    if (value != null) {
                        field.set(obj, value.trim());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
