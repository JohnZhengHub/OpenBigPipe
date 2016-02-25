package org.opensjp.openbigpipe.extention;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensjp.openbigpipe.view.PropertiesResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 * 拓展方法　的持有类
 * @author John Zheng
 *
 */
public class PageletExtendtionHolder {
	 private static PageletDowngrade downgrade;
	 private static PageletCache cache;
	 private static volatile boolean hasInit = false;
	 private static final PageletExtendtionHolder singleton = new PageletExtendtionHolder();
	 private PageletExtendtionHolder(){}
	 /**
	  * 获取单例
	  * @return
	  */
	 public static PageletExtendtionHolder singletonInstance(){
		 return singleton;
	 }
	 /**
	  * 初始化
	  * @param propsFilePath　配置文件路径
	  */
	 public static void loading(String propsFilePath){
		 if(hasInit == true)
			 return;
		 
		 synchronized(PageletExtendtionHolder.class){
			 if(hasInit == true)
				 return;
			 if(propsFilePath == null)
				return;
			 if(!StringUtils.hasText(propsFilePath))
				 throw new RuntimeException("There are not value for configPath of BigPipeAspect.The value of configPath can't"
				 		+ " be blankspace.");
			 
		 	 PropertiesResource propsResource = new PropertiesResource(propsFilePath);
			 List<String> extenKeys = Arrays.asList("cache", "backup");
			 Map<String, String> extenInfo = new HashMap<String, String>();
			 if(propsResource != null )
			 for(String key: extenKeys){
	         	String value = propsResource.getValue(key);
	         	if(value != null)
	         		extenInfo.put(key,value.trim());
			 }
			 initExtentionParam(extenInfo); 
			 hasInit = true;
		 }
	 }
	 
	 private static void initExtentionParam(Map<String, String> classNames) {
		if (CollectionUtils.isEmpty(classNames)) {
		    return;
		}
		for (String extenType : classNames.keySet()) {
		    try {
		        Class<?> clazz = Class.forName(classNames.get(extenType));
		        if ("downgrade".equals(extenType)) {
				    downgrade = (PageletDowngrade) clazz.newInstance();
				} else if ("cache".equals(extenType)) {
				    cache = (PageletCache) clazz.newInstance();
				}
			} catch (Exception e) {
				e.printStackTrace();
			    throw new RuntimeException("Init object error!");
			}
		}
	 }

	public PageletDowngrade getDowngrade() {
		return downgrade;
	}
	public PageletCache getCache() {
		return cache;
	}
}
