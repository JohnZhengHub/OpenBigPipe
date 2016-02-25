package org.opensjp.openbigpipe.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.opensjp.openbigpipe.core.filter.BigPipeRequestContextHolder;
import org.opensjp.openbigpipe.exception.BigPipeResultException;
import org.opensjp.openbigpipe.exception.RequestContextException;
import org.opensjp.openbigpipe.factory.BigPipeInterceptionProcessFactory;

/**
 * bigPipe切面．负责拦截 @BigPipe　的方法
 * @author john Zheng
 *
 */
@Aspect
public class BigPipeAspect{
	private BigPipeInterceptionProcess interceptionProcess;	
	private HttpServletRequest request;
	private HttpServletResponse response;	
	private String packageScan = "";
	private String configPath = null;
	public BigPipeAspect(){
		interceptionProcess = BigPipeInterceptionProcessFactory.singletonInstance();
	}
	/**
	 * 定义切点,拦截带有@BigPipe的方法
	 */
	@Pointcut("@annotation(org.opensjp.openbigpipe.annotation.BigPipe)")    
	public void bigPipe(){}
	
	@Before("bigPipe()")
	public void beforeAction(){
		interceptionProcess.beforeAction();
	}
	
	@Around("bigPipe()")
	public String doAction(ProceedingJoinPoint joinpoint){
		request = BigPipeRequestContextHolder.getRequest();
		response = BigPipeRequestContextHolder.getResponse();
		if(request == null || response == null){
			throw new RequestContextException("\n"
					+ "If you use openBigPipe plugin, you must make sure that you have"
					+ "set org.opensjp.openbigpipe.core.filter.BigPipeFilter filter and set it before filter of struts or springMVC in web.xml");
		}
		
        Signature signature = joinpoint.getSignature();    
        MethodSignature methodSignature = (MethodSignature)signature;    
        Method targetMethod = methodSignature.getMethod();    
        Class<?> targetClassType = joinpoint.getTarget().getClass();
        Object object = joinpoint.getTarget();
        Class<?> returnType = targetMethod.getReturnType();
        if(!String.class.isAssignableFrom(returnType)){
        	throw new BigPipeResultException("The result type of method with @Bigpipe must is java.lang.String.Error in"
        			+targetClassType.getName()+"."+targetMethod.getName());
        }        
        //交给拦截处理器处理
        return interceptionProcess.doAction(joinpoint,targetClassType, object, targetMethod,
        		request,response,packageScan,configPath);	        
	}
	
	@AfterThrowing(pointcut = "bigPipe()", throwing = "e")    
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		interceptionProcess.doAfterThrowing(joinPoint, e);
	}
	public String getPackageScan() {
		return packageScan;
	}
	public void setPackageScan(String packageScan) {
		this.packageScan = packageScan;
	}
	public String getConfigPath() {
		return configPath;
	}
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
}
