package com.example.springboot.aspect;

import com.example.springboot.config.DataSourceContext;
import com.example.springboot.enums.DataSourceEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(0)
public class DataSourceNodeAspect {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, org.springframework.transaction.annotation.Transactional transactional) throws Throwable {
        System.out.println("Aspect executed");
        try {
            if (transactional.readOnly()) {
                DataSourceContext.set(DataSourceEnum.SLAVE);
            } else {
                DataSourceContext.set(DataSourceEnum.MASTER);
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DataSourceContext.clear();
        }
    }
}
