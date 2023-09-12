package com.infy.EcommerceApp.logModule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * This class defines a Logger Aspect for logging method invocations and exceptions
 * in the com.infy.EcommerceApp.controller package.
 */
@Aspect
@Component
public class LoggerAspect {
    private static final Logger logger = LogManager.getLogger(LoggerAspect.class.getName());

    /**
     * AfterReturning advice method to log method invocations and their arguments.
     *
     * @param jp The JoinPoint representing the intercepted method call.
     * @throws JsonProcessingException If there is an issue serializing the method arguments to JSON.
     */
    @AfterReturning(value = "execution(* com.infy.EcommerceApp.controller.*.*(..))")
    public void applicationLogger(JoinPoint jp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String className = jp.getTarget().getClass().toString();
        String methodName = jp.getSignature().getName();
        Object[] array = jp.getArgs();

        logger.info(className.substring(39) + " " + methodName + " " + mapper.writeValueAsString(array));
    }

    /**
     * AfterThrowing advice method to log exceptions thrown by intercepted methods.
     *
     * @param jp The JoinPoint representing the intercepted method call.
     * @param ex The exception that was thrown.
     */
    @AfterThrowing(value = "execution(* com.infy.EcommerceApp.controller.*.*(..))", throwing = "ex")
    public void exceptionsLogger(JoinPoint jp, Exception ex) {
        String className = jp.getTarget().getClass().toString();
        String methodName = jp.getSignature().getName();

        logger.error(className.substring(39) + " " + methodName + " EXCEPTION: " + ex.getMessage());
    }
}











