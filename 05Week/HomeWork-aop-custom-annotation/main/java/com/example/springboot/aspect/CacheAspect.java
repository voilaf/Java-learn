package com.example.springboot.aspect;

import com.example.springboot.annotation.MyCache;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class CacheAspect {

    private static Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.example.springboot.annotation.MyCache)")
    public void cacheAction() {}

    @Around("cacheAction() && @annotation(myCache)")
    public Object cache(ProceedingJoinPoint joinPoint, MyCache myCache) throws Throwable {
        int now = (int)(System.currentTimeMillis() / 1000);
        Object result = null;
        Cache cache = null;

        String methodSignature = joinPoint.getSignature().toString();
        if (cacheMap.containsKey(methodSignature)) {
            cache = cacheMap.get(methodSignature);
            if (now <= cache.getDeadline()) {
                result = cache.getResult();
                System.out.println("读取缓存");
                return result;
            }
        }
        result = joinPoint.proceed();
        if (cache == null) {
            cache = new Cache();
        }
        cache.setResult(result);
        cache.setDeadline(now + myCache.value());
        cacheMap.putIfAbsent(methodSignature, cache);
        System.out.println("写入缓存");
        return result;
    }

    @Data
    public static class Cache {

        private int deadline;

        private Object result;
    }
}
