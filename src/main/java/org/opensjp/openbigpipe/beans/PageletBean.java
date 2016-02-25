package org.opensjp.openbigpipe.beans;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.annotation.Priority;
import org.opensjp.openbigpipe.view.ViewResource;

/**
 * 表示某一个可执行的Pagelet
 * @author John Zheng
 */
public class PageletBean{
	private static Logger logger = Logger.getLogger(PageletBean.class);
	/** Pagelet对应的	 **/
	private String key;
	/** 前台对应的divId **/
	private String divId;
	/** pagelet类的类型**/
	private Class<?> pageletClass;
	/** pagelet类对象 **/
	private Object pargletObject;
	/** Pagelet视图模板对应的视图 **/
	private ViewResource viewResource;
	/**　该Pagelet对应的执行方法 **/
	private Method executeMethod;
	/** 优先级 **/
	private Priority priority;
	/** 方法参数 **/
	private List<Object> methodParams;
	
	public PageletBean(String key, Object pargletObject, Method executeMethod, Priority weight) {
		this(key, key, pargletObject.getClass(), pargletObject, null, executeMethod, weight, new ArrayList<Object>());
	}
	
	public PageletBean(String key, String divId, Class<?> pageletClass, Object pargletObject, ViewResource viewResource,
			Method executeMethod, Priority priority, List<Object> methodParams) {
		super();
		this.key = key;
		this.divId = divId;
		this.pageletClass = pageletClass;
		this.pargletObject = pargletObject;
		this.viewResource = viewResource;
		this.executeMethod = executeMethod;
		this.priority = priority;
		this.methodParams = methodParams;
	}
	/**
	 * 执行pagelet
	 * @return
	 * @throws Throwable
	 */
	public String execute() throws Throwable {
		if(getPargletObject() == null){
			String message = "Object is null:\n The Object of pagelet is null. Please init "+pageletClass.getName()+" for the pagelet which key is "+key+".";
			logger.error(message);
			throw new RuntimeException(message);
		}
		Object[] args = new Object[getMethodParams().size()];
		int i = 0;
		for(Object param : getMethodParams()){
			args[i++] = param;
		}
		return (String)invoke( getPargletObject(), getExecuteMethod(),args);
	}
	/*
	 * 用反射机制调用
	 */
	private Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(proxy, args);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public Class<?> getPageletClass() {
		return pageletClass;
	}

	public void setPageletClass(Class<?> pageletClass) {
		this.pageletClass = pageletClass;
	}

	public Object getPargletObject() {
		return pargletObject;
	}

	public void setPargletObject(Object pargletObject) {
		this.pargletObject = pargletObject;
	}

	public ViewResource getViewResource() {
		return viewResource;
	}

	public void setViewResource(ViewResource viewResource) {
		this.viewResource = viewResource;
	}

	public Method getExecuteMethod() {
		return executeMethod;
	}

	public void setExecuteMethod(Method executeMethod) {
		this.executeMethod = executeMethod;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public List<Object> getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(List<Object> methodParams) {
		this.methodParams = methodParams;
	}
	
	
}
