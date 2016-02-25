package org.opensjp.openbigpipe.exception;

/**
 * 获取request　和response 时的错误.
 * 1.该错误发生在用户没有为插件设置过滤器(BigPipeFilter),使得插件获取不到request 和　response 请求.
 * 2.过滤器的位置设置有问题，必须设置在strut2 或　springMVC　之前，不然会被它们拦截
 * 
 * @author John Zheng
 *
 */
public class RequestContextException extends RuntimeException{
	private static final long serialVersionUID = -8278444190312294608L;

	public RequestContextException(String message){
		super(message);
	}
	
	public RequestContextException(Exception e){
		super(e);
	}
}
