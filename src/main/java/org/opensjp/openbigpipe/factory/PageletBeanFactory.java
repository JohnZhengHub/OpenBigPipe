package org.opensjp.openbigpipe.factory;

import java.util.Map;

import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;

/**
 * PageletBean 工厂，即根据pagelet的key　找到容器中对应的PageletBeanDefinition．然后生产一个Bean．
 * @author john Zheng
 *
 */
public interface PageletBeanFactory {
	
	/**
	 * 根据key值　获取一个PageletBean.
	 * @param key
	 * @return
	 */
	public PageletBean getPageletBean(String key,Map<String,Object> fieldValuesMap);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public PageletBean getPageletBean(String key,BigPipeBean bigPipeBean);
	
}
