package org.opensjp.openbigpipe.extention;

import org.opensjp.openbigpipe.beans.BigPipeBean;

/**
 * 模块备份接口
 *在一个模块渲染完之后，会回调实现这个接口的类，备份策略由实现类自行制定
 */
public interface PageletBackup {
	/**
	 * 备份渲染的结果
	 * @param pagelet
	 * @param pipeResult
	 * @return 备份成功返回true,否则false
	 */
    boolean backup(BigPipeBean pagelet, String pipeResult);
    
}
