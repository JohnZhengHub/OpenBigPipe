package org.opensjp.openbigpipe.exception;

/**
 * 初始化插件异常
 * @author John Zheng
 *
 */
public class InitPluginException extends RuntimeException{

	private static final long serialVersionUID = -5719177191607094380L;

	public InitPluginException(String message){
		super(message);
	}
	
	public InitPluginException(Exception e){
		super(e);
	}
}
