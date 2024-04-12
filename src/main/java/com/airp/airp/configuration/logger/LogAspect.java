package com.airp.airp.configuration.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.airp.airp.security.SecurityUtils.lireLoginUtilisateurConnecte;

@Aspect
@Component
public class LogAspect {
    public static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(Logged)")
    public void startAProcess(JoinPoint joinPoint) throws Throwable {
        logger.info(lireLoginUtilisateurConnecte() + " - " + joinPoint.getSignature().getName() +  " - " + Arrays.toString(joinPoint.getArgs()));
    }
}
