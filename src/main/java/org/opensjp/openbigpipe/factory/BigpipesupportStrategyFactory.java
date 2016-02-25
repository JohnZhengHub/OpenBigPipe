package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.core.BigpipeViewFrameStrategy;
import org.opensjp.openbigpipe.core.SimpleBigpipeViewFrameSupport;

/**
 * 生成　BigpipesupportStrategy
 * @author John Zheng
 *
 */
public class BigpipesupportStrategyFactory {
	/**
	 * 获取一个BigpipeSupportStrategy
	 * @return
	 */
	public static BigpipeViewFrameStrategy newInstance(){
		return SimpleBigpipeViewFrameSupport.newInstance();
	}
}
