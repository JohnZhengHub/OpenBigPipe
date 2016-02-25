package org.opensjp.openbigpipe.exception;

/**
 * 视图资源异常　
 * @author John Zheng 
 *
 */
public class ViewResourceException extends ResourceException{

	private static final long serialVersionUID = 6935188720015760356L;

	public ViewResourceException(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	public ViewResourceException(String message){
		super(message);
	}

}
