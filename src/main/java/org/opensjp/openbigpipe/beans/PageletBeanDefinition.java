package org.opensjp.openbigpipe.beans;

import java.util.List;

import org.opensjp.openbigpipe.annotation.Priority;
/**
 * pagelet的定义，用于生成pageletBean. 
 * 如果一个类被标注为@PageletSet,且某个方法带有＠Pagelet的注解．那么该带有＠Pagelet
 * 的方法代表一个Pagelet，那将该pagelet的定义保存到PageletBeanDefinition．
 * @author John Zheng
 *
 */
public class PageletBeanDefinition {
	/** Pagelet对应的	key **/
	private String key;
	/** pagelet类的类型**/
	private Class<?> pageletClass;
	/** @pagelet 修饰的方法名字 **/
	private String methodName;
	/** 方法参数类型 **/
	private Class<?>[] methodParamTypes;
	/** 优先级 **/
	private Priority priority;
	

	public PageletBeanDefinition(String key, Class<?> pageletClass, String methodName,Class<?>[] methodParamTypes, Priority priority) {
		super();
		this.key = key;
		this.pageletClass = pageletClass;
		this.methodName = methodName;
		this.methodParamTypes = methodParamTypes;
		this.priority = priority;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getMethodParamTypes() {
		return methodParamTypes;
	}

	public void setMethodParamTypes(Class<?>[] methodParamTypes) {
		this.methodParamTypes = methodParamTypes;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Class<?> getPageletClass() {
		return pageletClass;
	}

	public void setPageletClass(Class<?> pageletClass) {
		this.pageletClass = pageletClass;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
