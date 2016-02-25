package org.opensjp.openbigpipe.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensjp.openbigpipe.annotation.ExecuteType;
import org.opensjp.openbigpipe.view.ViewResource;

/**
 * 保存BigPipe的请求信息．例如：
 * 一个Action 的方法采用了　@BigPipe　注解．那么将该类的信息和对应的方法信息全部封转成一个BigPipeBean.
 * @author John Zheng
 * Date: 2015.11.10
 */
public class BigPipeBean {
	/** 请求　**/
	private HttpServletRequest request;
	/**　响应　**/
	private HttpServletResponse response;
	/** 所有的pagelet的key值**/
	private String[] pageletKeys;
	/** 所有的pagelet的div　id和类key的映射关系　**/
	private Map<String,String> pageletMap;
	/** 所有属性参数 **/
	private Map<String,Object> fieldParams;
	/** 方法参数 **/
	private List<Object> methodParams;
	/**　视图资源　**/
	private  ViewResource viewResource;
	/**　执行方式　**/
	private ExecuteType type = ExecuteType.BIGPIPE;
	/**　对应类的类型**/
	private Class<?> targetClassType;
	/** 对应类对象 **/
	private Object object;
	/** 对应方法名字 **/
	private String targetMethodName;
	
	public BigPipeBean(ExecuteType type){
		this(null,null,type);
	}
	public BigPipeBean(HttpServletRequest request, HttpServletResponse response, 
			ExecuteType type) {
		this(request,response,type,new HashMap<String, Object>(),new ArrayList<Object>());
	}
	public BigPipeBean(HttpServletRequest request, HttpServletResponse response,
			ExecuteType type,Map<String, Object> fieldParams, List<Object> methodParams) {
		super();
		this.type = type;
		this.request = request;
		this.response = response;
		this.fieldParams = fieldParams;
		this.methodParams = methodParams;	
	}
	/**
	 * 添加
	 * @param string
	 * @param param
	 */
	public void addFieldParam(String string,Object param) {
		this.fieldParams.put(string, param);
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String[] getPageletKeys() {
		return pageletKeys;
	}
	public void setPageletKeys(String[] pageletKeys) {
		this.pageletKeys = pageletKeys;
	}
	public Map<String, String> getPageletMap() {
		return pageletMap;
	}
	public void setPageletMap(Map<String, String> pageletMap) {
		this.pageletMap = pageletMap;
	}
	public Map<String, Object> getFieldParams() {
		return fieldParams;
	}
	public void setFieldParams(Map<String, Object> fieldParams) {
		this.fieldParams = fieldParams;
	}

	public ViewResource getViewResource() {
		return viewResource;
	}
	public void setViewResource(ViewResource viewResource) {
		this.viewResource = viewResource;
	}
	public ExecuteType getType() {
		return type;
	}
	public void setType(ExecuteType type) {
		this.type = type;
	}
	public Class<?> getTargetClassType() {
		return targetClassType;
	}
	public void setTargetClassType(Class<?> targetClassType) {
		this.targetClassType = targetClassType;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getTargetMethodName() {
		return targetMethodName;
	}
	public void setTargetMethodName(String targetMethodName) {
		this.targetMethodName = targetMethodName;
	}
	public List<Object> getMethodParams() {
		return methodParams;
	}
	public void setMethodParams(List<Object> methodParams) {
		this.methodParams = methodParams;
	}
	
}
