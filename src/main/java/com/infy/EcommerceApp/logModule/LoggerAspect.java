package com.infy.EcommerceApp.logModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class LoggerAspect {
    private static final Logger logger = LogManager.getLogger(LoggerAspect.class.getName());

    @After(value="execution(* com.infy.EcommerceApp.GetMapping)")
        public void afterAdvice(JoinPoint jp){
        logger.info();
    }
    }








}
