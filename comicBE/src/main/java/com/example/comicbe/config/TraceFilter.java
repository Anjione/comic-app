package com.example.comicbe.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class TraceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Extract or generate traceId
            String traceId = UUID.randomUUID().toString();


            // Extract or generate spanId
            String spanId = UUID.randomUUID().toString();

            // Set traceId & spanId in MDC (Mapped Diagnostic Context)
            MDC.put("traceId", traceId);
            MDC.put("spanId", spanId);

            // Continue request processing
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear(); // Prevent leakage between requests
        }
    }
}