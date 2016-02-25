package org.opensjp.openbigpipe.core;

import java.util.Map;
/**
 * 
 * 插件的中央控制器，负责从struts action（或spring mvc的controller）获得请求，
 * 按执行的类型转发给不同的执行器（BigPipeExecutor,ConcurrentExecutor）去执行.
 * @author John Zheng
 * 
 */
public interface BigPipeController {
	/**
	 * 该方法是整个框架的核心，负责执行请求.
	 * @param viewFrame  页面视图框架 相对路径
	 * @param pageletKeys 　pagelet的key值数据．
	 * @return
	 */
	public String execute(String viewFrame,String[] pageletKeys);
	/**
	 * 
	 * @param viewFrame 视图框架
	 * @param pageletMap　各个pagelet的div id和具体的pagelet　key映射关系
	 * @return
	 */
	public String execute(String viewFrame,Map<String,String> pageletMap);
		
}
