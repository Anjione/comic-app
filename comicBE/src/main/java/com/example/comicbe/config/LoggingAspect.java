package com.example.comicbe.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes
                ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                : null;

        if (request != null) {
            // Log request headers
            Enumeration<String> headerNames = request.getHeaderNames();
            Map<String, String> headers = new HashMap<>();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                headers.put(header.toLowerCase(), request.getHeader(header));
            }
            logger.info("Incoming Request: {} {} from IP: {} - requestId: {}",
                    request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), headers.get("requestid"));
        }

        // Proceed with the method execution
        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Response (Time Taken: {} ms)", duration);

        return result;
    }
}
