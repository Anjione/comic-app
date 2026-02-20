package com.example.comicbe.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpServerUtils {

    private static final String HTTPS_SCHEME = "https";
    private static final String HTTP_SCHEME = "http";

    public static String getBaseUrlFromServletRequest(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals(HTTP_SCHEME) && serverPort != 80) || (scheme.equals(HTTPS_SCHEME) && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }
}
