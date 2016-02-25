package org.opensjp.openbigpipe.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.core.AbstractBigPipeController;
import org.opensjp.openbigpipe.core.BigPipeBeanParser;
import org.opensjp.openbigpipe.exception.BigPipeResultException;
import org.opensjp.openbigpipe.factory.BigPipeBeanDefinitionProcessorFactory;
import org.opensjp.openbigpipe.factory.BigPipeExecutorFactory;
import org.opensjp.openbigpipe.utils.InjectUtils;
/**
 * 一个简单的拦截处理实现
 * @author John Zheng
 *
 */
public class SimpleBigPipeInterceptionProcess implements BigPipeInterceptionProcess{
	private static Logger logger = Logger.getLogger(SimpleBigPipeInterceptionProcess.class); 
	private BigPipeBeanParser bigPipeBDPorcessor;
	
	@Override
	public void beforeAction() {
		bigPipeBDPorcessor = BigPipeBeanDefinitionProcessorFactory.singletonInstance();
	}
	
	@Override
	public String doAction(ProceedingJoinPoint joinpoint, Class<?> targetClassType, Object object, Method targetMethod,
			HttpServletRequest request, HttpServletResponse response,String packageScan,String configPath) {
		//解析
		BigPipeBean bpbDefinition = this.processControllerWithAnno(targetClassType,object,targetMethod,request,response);
		AbstractBigPipeController bigPipeExecutor = null;
		try{
			//每个请求创建一个controller
			bigPipeExecutor = BigPipeExecutorFactory.newInstance();
			//初始化插件
			bigPipeExecutor.initPlugin(packageScan,configPath);
		}catch(Exception e){
			logger.debug("Init openBigPipe error");
			throw new RuntimeException(e);
		}
		
		bigPipeExecutor.setBigPipeBean(bpbDefinition);
		//向对象中　含有@Param 的BigPipeController域注入bigPipeController 对象
		InjectUtils.injectBigPipeControllerＷithParamAnno(object,bigPipeExecutor);
		
		Object result = null;
		try {
			result = joinpoint.proceed();
		} catch (Throwable e) {
			logger.debug("BigPipe execute error.\n "
					+targetClassType.getName()+"."+targetMethod.getName()+"() execute error. Please check it.");
			throw new BigPipeResultException(e);
		}
		 
		return (String)result;
	}

	@Override
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}

	/**
	 * 解析出　BigPipeBean
	 * @param classType
	 * @param action
	 * @param bigPipeMethod
	 * @return
	 */
	private BigPipeBean processControllerWithAnno(Class<?> classType,Object action
			, Method bigPipeMethod,HttpServletRequest request, HttpServletResponse response) {
		
		//如果方法有＠BigPipe注解
		BigPipeBean bpbDefinition = getBigPipeBDPorcessor().parseBigPipeAnno(classType
				, action, bigPipeMethod);
		bpbDefinition.setRequest(request);
		bpbDefinition.setResponse(response);
		return bpbDefinition;
	}

	public BigPipeBeanParser getBigPipeBDPorcessor() {
		if(bigPipeBDPorcessor == null)
			bigPipeBDPorcessor = BigPipeBeanDefinitionProcessorFactory.singletonInstance();
		return bigPipeBDPorcessor;
	}

	

}
