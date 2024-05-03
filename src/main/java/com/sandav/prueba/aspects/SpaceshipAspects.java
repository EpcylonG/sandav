package com.sandav.prueba.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.sandav.prueba.utils.LoggerController;

@Component
@Aspect
public class SpaceshipAspects {

    private LoggerController logger = new LoggerController();

    @Pointcut("execution(* com.sandav.prueba.controller.SpaceshipController.getById(..)) && args(id)")
    private void getById(long id) {}

    @AfterReturning(pointcut = "getById(id)", returning = "result")
    public void afterReturning(Object result, long id) {
        if (id < 0) {
            logger.info("El id devuelto es negativo.");
        }
    }
}