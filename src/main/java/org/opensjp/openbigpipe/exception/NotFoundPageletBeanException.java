package org.opensjp.openbigpipe.exception;

/**
 * PageletBean 找不到异常
 * @author John Zheng
 *
 */
public class NotFoundPageletBeanException extends RuntimeException{
	private static final long serialVersionUID = -1679818031409757081L;
	
	public NotFoundPageletBeanException(String message){
		super(message);
	}
	
	public NotFoundPageletBeanException(Exception e){
		super(e);
	}
}
