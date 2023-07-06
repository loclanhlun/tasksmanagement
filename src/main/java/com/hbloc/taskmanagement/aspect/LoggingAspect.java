package com.hbloc.taskmanagement.aspect;

import com.google.gson.Gson;
import com.hbloc.taskmanagement.request.AuthenticateRequest;
import com.hbloc.taskmanagement.request.RegisterRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
@Configuration
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.hbloc.taskmanagement.api..*(..))")
    public void beforeCallApi(JoinPoint joinPoint) throws Throwable {
        logger.info("Call API Starting...");
    }

    @AfterReturning(value = "execution(* com.hbloc.taskmanagement.api..*(..))", returning = "result")
    public void afterCallApi(JoinPoint joinPoint, Object result) throws Throwable {
        Gson gson = new Gson();
        logger.info("Response: " + gson.toJson(((ResponseEntity<?>) result).getBody()));
        logger.info("Call API Stopped");
    }

}
