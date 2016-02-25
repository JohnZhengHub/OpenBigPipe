package org.opensjp.openbigpipe.exception;

public class BigPipeRunException extends RuntimeException{

	private static final long serialVersionUID = -4109099354105727981L;

	public BigPipeRunException(String message){
		super(message);
	}
	
	public BigPipeRunException(Exception e){
		super(e);
	}
}
