package org.opensjp.openbigpipe.exception;

/**
 * 注册错误　
 * @author john Zheng
 *
 */
public class PageletBeanDefinitionRegistryException extends RuntimeException{
	private static final long serialVersionUID = -8077014835846691799L;
	
	public PageletBeanDefinitionRegistryException(String message){
		super(message);
	}
	
	public PageletBeanDefinitionRegistryException(Exception e){
		super(e);
	}

}
