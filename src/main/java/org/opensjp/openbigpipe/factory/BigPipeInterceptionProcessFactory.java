package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.core.interceptor.BigPipeInterceptionProcess;
import org.opensjp.openbigpipe.core.interceptor.SimpleBigPipeInterceptionProcess;

/**
 * 生产拦截处理器BigPipeInterceptionProcess
 * @author John Zheng
 *
 */
public class BigPipeInterceptionProcessFactory {
	private static final BigPipeInterceptionProcess singleton = new SimpleBigPipeInterceptionProcess();
	
	/**
	 * 获取单例
	 * @return
	 */
	public static BigPipeInterceptionProcess singletonInstance(){
		return singleton;
	}
}
