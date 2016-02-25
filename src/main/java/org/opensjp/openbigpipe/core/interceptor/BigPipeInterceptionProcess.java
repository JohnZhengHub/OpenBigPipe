package org.opensjp.openbigpipe.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.opensjp.openbigpipe.beans.BigPipeBean;
/**
 * 对于拦截到的具有@BigPipe 的行为的处处理.　主要是作为插件的一个入口
 * @author John Zheng
 *
 */
public interface BigPipeInterceptionProcess {
	/**
	 * 拦截前处理
	 */
	public void beforeAction();
	/**
	 * 拦截处理
	 * @param joinpoint　切点
	 * @param targetClassType　拦截的对象类型
	 * @param object　　拦截的对象
	 * @param targetMethod　拦截方法
	 * @param request 前端请求
	 * @param response　响应
	 */
	public String doAction(ProceedingJoinPoint joinpoint, Class<?> targetClassType, Object object, Method targetMethod,
			HttpServletRequest request, HttpServletResponse response,String packageScan,String configPath);

	
	/**
	 * 异常处理
	 * @param joinPoint
	 * @param e
	 */
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e);
	
}
