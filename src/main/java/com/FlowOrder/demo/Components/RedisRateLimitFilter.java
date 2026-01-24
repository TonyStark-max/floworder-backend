package com.FlowOrder.demo.Components;


import com.FlowOrder.demo.Events.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisRateLimitFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;
    private static final int LIMIT=5;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        String path=request.getRequestURI();

        if(path.startsWith("/api/payments")){
            Integer userId= SecurityUtil.getCurrentUserId();
            String key=buildKey(userId);

            Long count=redisTemplate.opsForValue().increment(key);

            if(count==1){
                redisTemplate.expire(key, Duration.ofMinutes(1));
            }

            if(count>LIMIT){
                response.setStatus(429);
                response.getWriter().write("Too many attempts");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    private String buildKey(Integer userId){
        long minute=System.currentTimeMillis()/6000;
        return "rate:payments"+userId+":"+minute;
    }
}
