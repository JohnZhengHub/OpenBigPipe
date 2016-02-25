package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.core.AbstractBigPipeController;
import org.opensjp.openbigpipe.core.DefaultBigPipeController;

/**
 * BigPipeController　工厂
 * @author John Zheng
 * Date: 2015.11.10
 */
public class BigPipeExecutorFactory {
	private static final AbstractBigPipeController singleton = new DefaultBigPipeController();
	/**
	 * 获取 BigPipeController
	 * @return
	 */
	public static AbstractBigPipeController singletonInstance(){
		return singleton;
	}
	/**
	 * 返回一个实例，每次都不同
	 * @return
	 */
	public static AbstractBigPipeController newInstance(){
		return new DefaultBigPipeController();
	}
}
