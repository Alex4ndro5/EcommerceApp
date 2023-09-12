package com.infy.EcommerceApp.logModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
/*import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning; */
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class LoggerAspect {
    private static final Logger logger = LogManager.getLogger(LoggerAspect.class.getName());

    /*@Pointcut(value="execution(* com.infy.EcommerceApp.controller.*.*(..) )")
    public void myPointCut () {
    }*/

    @AfterReturning(value="execution(* com.infy.EcommerceApp.controller.*.*(..) )")
    public void applicationLogger(JoinPoint jp) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String className = jp.getTarget().getClass().toString();
        String methodName = jp.getSignature().getName();
        Object[] array = jp.getArgs();

        logger.info(className.substring(39) + " "+ methodName + " " + mapper.writeValueAsString(array));

    }


   @AfterThrowing(value="execution(* com.infy.EcommerceApp.controller.*.*(..) )", throwing = "ex")
    public void exceptionsLogger(JoinPoint jp, Exception ex) {

        String className = jp.getTarget().getClass().toString();
        String methodName = jp.getSignature().getName();

        logger.error(className.substring(39) + " " + methodName + " EXCEPTION: " + ex.getMessage());
    }
    }











