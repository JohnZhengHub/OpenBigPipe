package org.opensjp.openbigpipe.extention;

import org.opensjp.openbigpipe.beans.PageletBean;

/**
 * 模块降级接口
 * 当模块渲染抛出异常时，会回调此接口
 */
public interface PageletDowngrade{
	/**
	 * 如果执行pagelt方法时，发生异常的应急方式．
	 * @param pagelet　发生异常的pagelet
	 * @return　放回一个String类型的页面内容
	 */
	String downgrade(PageletBean pagelet);
}
