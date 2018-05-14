package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// setup point cut declaration
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	public void forControllerPackage() {}
	
	// do the same for service and dao
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	public void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	public void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	public void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		// display method we are calling
		String method = joinPoint.getSignature().toShortString();
		logger.info("===> in the @Before: calling method: " + method);
		
		// display the arguments to the method		
		// get the arguments
		Object[] args = joinPoint.getArgs();
		
		// loop thru and display
		for (Object tempArg : args) {
			logger.info("======> argument: " + tempArg);
		}	
		
	}
	
	
	// add @AfterReturning advice
	@AfterReturning(
	        pointcut = "forAppFlow()",
            returning = "result"
    )
	public void after(JoinPoint joinPoint, Object result) {

		// display method we are returning from
		String method = joinPoint.getSignature().toShortString();
		logger.info("===> in the @AfterReturning: from method: " + method);

		// display the data returned
		logger.info("===> result: " + result);

	}
}
