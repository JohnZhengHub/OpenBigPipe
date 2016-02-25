package org.opensjp.openbigpipe.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/**
 * 抽象的资源接口
 * @author john Zheng
 * Date: 2015.11.10
 */
public interface Resource 
{
	/**
	 * 获取输入流
	 * @return
	 */
	InputStream getInputSteam();
	/**
	 * 获得相对路径
	 * @return
	 */
	String getRelativePath();
	/**
	 * 获得绝对路径
	 * @return
	 */
	String getAbsolutePath();
	/**
	 * 判断是否存在
	 * @return
	 */
	boolean exist();
	/**
	 * 获取文件
	 * @return
	 * @throws IOException
	 */
	File getFile() throws IOException;
	
	String getDescription();
}
