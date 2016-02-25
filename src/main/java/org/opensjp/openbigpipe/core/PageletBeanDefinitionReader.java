package org.opensjp.openbigpipe.core;

import java.util.List;

/**
 * 查找含有该openBigPipe插件的类注解(如@PageletSet)的类
 * 默认从项目项目类目录下寻找.
 * 
 * @author John Zheng
 *　Date: 2015.11.9
 */
public interface PageletBeanDefinitionReader {
	/**
	 * 默认从项目项目类目录下查找含有该插件类注解(如@PageletSet)的类
	 * @return　返回含有该插件类注解的类
	 */
	public List<Class<?>> findPageletClasses(); 
	
	/**
	 * 默认从项目项目类目录下查找含有该插件类注解(如@PageletSet)的类
	 * @param classPath
	 * @return　返回含有该插件类注解的类
	 */
	public List<Class<?>> findPageletClasses(String classPath); 
	
}
