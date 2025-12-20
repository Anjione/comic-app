package com.example.comicbe.utils;

public class ConvertUtils {

    private static final String os = System.getProperty("os.name").toLowerCase();
    public static String sanitizeFileName(String name) {

        if (os.contains("win")) {
            return name.replaceAll("[\\\\/:*?\"<>|]", "_");
        }
        return name.replace("/", "_");
    }
}
