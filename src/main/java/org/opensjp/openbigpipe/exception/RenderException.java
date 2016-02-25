package org.opensjp.openbigpipe.exception;
/**
 * 渲染异常
 * @author John Zheng
 *
 */
public class RenderException extends RuntimeException{
	private static final long serialVersionUID = -1679818031409757081L;
	
	public RenderException(String message){
		super(message);
	}
	
	public RenderException(Exception e){
		super(e);
	}
}
