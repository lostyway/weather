package com.weather.configuration.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class AopLogging {

    @Around("execution(* com.weather..*(..)) "
            + "&& !within(com.weather.configuration.security..*)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String params = args.length > 0 ? Arrays.toString(args) : "без параметров";

        log.debug("Запущен метод: {}. С параметрами: {}", methodName, params);
        Object result = joinPoint.proceed(args);

        log.debug("Метод {} завершился. Результат: {}", methodName, result);
        return result;
    }
}
