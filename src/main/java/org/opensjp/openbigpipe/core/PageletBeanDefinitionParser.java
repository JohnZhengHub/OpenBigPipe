package org.opensjp.openbigpipe.core;

import java.util.List;

import org.opensjp.openbigpipe.beans.PageletBeanDefinition;

/**
 * 从一个含有＠PageletSet注解的类中解析出所有带@Pagelet的方法，得到对应的PageletBeanDefinition
 * @author John Zheng
 *
 */
public interface PageletBeanDefinitionParser {
	/**
	 * 从一个含有＠PageletSet注解的类中解析出所有带@Pagelet的方法，得到对应的PageletBean
	 * @param classType
	 * @return
	 */
	public List<PageletBeanDefinition> parsePageletBeanDefinition(Class<?> classType);
}
