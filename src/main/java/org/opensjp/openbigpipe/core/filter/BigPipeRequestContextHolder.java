package org.opensjp.openbigpipe.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 保存当前线程的request 和　response 
 * @author john Zheng
 *
 */
public class BigPipeRequestContextHolder {
	//当前线程的　request
	private static ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal<HttpServletRequest>(); 
	//　当前线程的　response
	private static ThreadLocal<HttpServletResponse> responseLocal= new ThreadLocal<HttpServletResponse>();   
       
	public static HttpServletRequest getRequest() {   
		return (HttpServletRequest)requestLocal.get();   
	} 	  
	public static void setRequest(HttpServletRequest request) {   
		requestLocal.set(request);   
	}   
	public static HttpServletResponse getResponse() {   
		return (HttpServletResponse)responseLocal.get();   
	}   
	public static void setResponse(HttpServletResponse response) {   
		responseLocal.set(response);   
	}   
	public static HttpSession getSession() {   
		return (HttpSession)((HttpServletRequest)requestLocal.get()).getSession();   
	}   
}
