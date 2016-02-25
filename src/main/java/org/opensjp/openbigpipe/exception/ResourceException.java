package org.opensjp.openbigpipe.exception;

/**
 * 视图资源错误
 * @author John Zheng
 *
 */
public class ResourceException extends RuntimeException{
	private static final long serialVersionUID = 5630427634897455880L;
	public ResourceException(String message){
		super(message);
	}
	
	public ResourceException(Exception e){
		super(e);
	}
}