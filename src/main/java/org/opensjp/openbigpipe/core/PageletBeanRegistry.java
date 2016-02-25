package org.opensjp.openbigpipe.core;

import java.util.List;

import org.opensjp.openbigpipe.beans.PageletBeanDefinition;

/**
 * 注解PageletBeanDefinition　到容器中
 * @author John Zheng
 *
 */
public interface PageletBeanRegistry {
	/**
	 * 注解PageletBeanDefinition　到容器中
	 * @param pBeanDefinition
	 */
	public boolean registryPageletBeanDefinition(PageletBeanDefinition pBeanDefinition);
	/**
	 * 
	 * @param pBeanDefinitions
	 */
	public boolean registryPageletBeanDefinition(List<PageletBeanDefinition> pBeanDefinitions);
	
	/**
	 * 看key是否存在
	 * @param key
	 * @return
	 */
	public boolean isExistKey(String key);
}
