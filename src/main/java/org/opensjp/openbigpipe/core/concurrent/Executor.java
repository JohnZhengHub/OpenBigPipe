package org.opensjp.openbigpipe.core.concurrent;

import java.util.List;

import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;

/**
 * 执行器　
 * @author John Zheng
 *
 */
public interface Executor {
	/**
	 * 执行　pagelet动作
	 * @param pageletBean
	 * @return
	 */
	public void execute(BigPipeBean bigPipeBean,PageletBean pageletBean) throws Exception;
	
	/**
	 * 执行　一系列pagelet
	 * @param pageletBean
	 * @return
	 */
	public void execute(BigPipeBean bigPipeBean,List<PageletBean> pageletBean) throws Exception;
}
