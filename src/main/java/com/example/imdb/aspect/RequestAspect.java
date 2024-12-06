package com.example.imdb.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class RequestAspect {

    private AtomicInteger numberOfRequest = new AtomicInteger(0);

    @Before("execution(* org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handle(..))")
    public void countRequest() {
        numberOfRequest.incrementAndGet();
    }

    public int getRequestCount() {
        return numberOfRequest.get();
    }
}
