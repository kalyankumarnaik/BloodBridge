package in.bloodbridge.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RateLimitingFilter.class);


    private final int generalLimit;
    private final int authLimit;

    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    public RateLimitingFilter(
            @Value("${bloodbridge.security.rate-limit.requests-per-minute:120}") int generalLimit,
            @Value("${bloodbridge.security.rate-limit.auth-requests-per-minute:25}") int authLimit) {
        this.generalLimit = generalLimit;
        this.authLimit = authLimit;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = getClientIp(request);
        String uri = request.getRequestURI();

        boolean isAuthEndpoint = uri.contains("/api/v1/auth/login") || uri.contains("/api/v1/auth/register");
        int limit = isAuthEndpoint ? authLimit : generalLimit;
        String key = clientIp + ":" + (isAuthEndpoint ? "auth" : "gen");

        long currentTime = System.currentTimeMillis();
        RequestCounter counter = requestCounts.compute(key, (k, v) -> {
            if (v == null || currentTime - v.windowStartTime > 60000) {
                return new RequestCounter(currentTime, new AtomicInteger(1));
            }
            v.counter.incrementAndGet();
            return v;
        });

        if (counter.counter.get() > limit) {
            log.warn("Rate limit exceeded for IP: {} on URI: {}", clientIp, uri);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", "60");
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":429,\"code\":\"RATE_LIMIT_EXCEEDED\",\"message\":\"Too many requests. Please retry in 60 seconds.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(",")) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    private static class RequestCounter {
        final long windowStartTime;
        final AtomicInteger counter;

        RequestCounter(long windowStartTime, AtomicInteger counter) {
            this.windowStartTime = windowStartTime;
            this.counter = counter;
        }
    }
}
