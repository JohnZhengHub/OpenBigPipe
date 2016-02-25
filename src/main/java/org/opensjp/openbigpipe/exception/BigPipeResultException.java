package org.opensjp.openbigpipe.exception;

/**
 * action(struts action /springMVC controller)　返回结果处理错误
 * @author John Zheng
 *
 */
public class BigPipeResultException extends RuntimeException{
	private static final long serialVersionUID = -1679818031409757081L;
	
	public BigPipeResultException(String message){
		super(message);
	}
	
	public BigPipeResultException(Exception e){
		super(e);
	}

	public BigPipeResultException(Throwable e) {
		super(e);
	}
}
