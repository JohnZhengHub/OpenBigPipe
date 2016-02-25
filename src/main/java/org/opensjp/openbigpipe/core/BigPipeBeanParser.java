package org.opensjp.openbigpipe.core;

import java.lang.reflect.Method;

import org.opensjp.openbigpipe.beans.BigPipeBean;
/**
 * 从action类中解析得到BigPipeBean.
 * 如果action中的某个方法带有　@BigPipe 注解，则解析该类和对应方法的信息，构建一个BigPipeBean.
 * @author John Zheng
 *
 */
public interface BigPipeBeanParser {
	/**
	 * 从指定的类和方法中解析出　BigPipeBean
	 * @param classType
	 * @param action
	 * @param bigPipeMethod
	 * @return
	 */
	public BigPipeBean parseBigPipeAnno(Class<?> classType,Object action,Method bigPipeMethod);
	
	
}
