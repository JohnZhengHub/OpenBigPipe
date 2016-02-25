package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.core.BigPipeBeanParser;
import org.opensjp.openbigpipe.core.SimpleBigPipeBeanParser;

/**
 * BigPipeBeanDefinitionProcessor 的工厂类
 * @author John Zheng
 *
 */
public class BigPipeBeanDefinitionProcessorFactory {
	private static final BigPipeBeanParser singletonProcessor = new SimpleBigPipeBeanParser();
	/**
	 * 生产一个BigPipeBeanDefinitionProcessor
	 * @return　BigPipeBeanDefinitionProcessor对象
	 */
	public static BigPipeBeanParser newInstance(){
		return new SimpleBigPipeBeanParser();
	}
	/**
	 * 获取单例　
	 * @return
	 */
	public static BigPipeBeanParser singletonInstance(){
		return singletonProcessor;
	}
}
