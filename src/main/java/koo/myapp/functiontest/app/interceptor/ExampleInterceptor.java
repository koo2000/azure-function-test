package koo.myapp.functiontest.app.interceptor;

import java.util.ArrayList;
import java.util.Collections;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExampleInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Around("execution (* koo.myapp.functiontest.app.function*..*(..))")
    public Object aroundExample(ProceedingJoinPoint jp) throws Throwable {

        Object[] args = jp.getArgs();
        logger.info(" pre execute " + jp.toString());
        logger.info(" args " + Collections.singletonList(args).toString());
        Object obj;

        try {
            obj = jp.proceed();
        } catch (Throwable t) {
            logger.error("execute failed" + t.getMessage(), t);
            throw t;
        }
        logger.info(" post execute " + jp.toString());
        
        return obj;
    }
}
