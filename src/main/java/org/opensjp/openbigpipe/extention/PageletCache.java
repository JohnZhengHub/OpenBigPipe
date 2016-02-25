package org.opensjp.openbigpipe.extention;

import org.opensjp.openbigpipe.beans.PageletBean;

/**
 * 模块缓存接口
 * 在渲染模块之前先通过回调此接口获取模块结果，
 * 如果为null，则继续执行渲染模块代码
 * 如果不为null，则直接使用接口返回作为模块渲染接口
 */
public interface PageletCache{
	/**
	 * 从缓冲中取出对应的数据
	 * @param pagelet
	 * @return
	 */
    String getCachedPageletResult(PageletBean pagelet);
    
    /**
	 * 备份渲染的结果
	 * @param pagelet
	 * @param pipeResult
	 * @return 备份成功返回true,否则false
	 */
    boolean backup(PageletBean pagelet, String pipeResult);
}
