package org.opensjp.openbigpipe.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.annotation.Priority;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;
import org.opensjp.openbigpipe.beans.PageletBeanDefinition;
import org.opensjp.openbigpipe.core.PageletBeanRegistry;
import org.opensjp.openbigpipe.exception.PageletBeanDefinitionRegistryException;
import org.opensjp.openbigpipe.utils.InjectUtils;
import org.springframework.util.Assert;

/**
 * 　
 * @author John Zheng
 *
 */
public class ListablePageletBeanFactory implements PageletBeanFactory,PageletBeanRegistry{
	private static Logger logger = Logger.getLogger(ListablePageletBeanFactory.class);
	//存放pageletBeanDefinition　的容器
	private static Map<String,PageletBeanDefinition> pageletBeanMap = new ConcurrentHashMap<String,PageletBeanDefinition>();
	/**
	 * 查看key是否存在
	 * @param key
	 * @return
	 */
	@Override
	public boolean isExistKey(String key){
		return pageletBeanMap.containsKey(key);
	}
	
	/**
	 * 注册一个　PageletBeanDefinition
	 * @param key
	 * @param pbDefinition
	 */
	@Override
	public boolean registryPageletBeanDefinition(PageletBeanDefinition pBeanDefinition) {
		Assert.notNull(pBeanDefinition, "PageletBeanDefinition must not be null");
		
		String key = pBeanDefinition.getKey();
		Assert.hasText(key, "PageletBean name must not be empty");
		
		if(isExistKey(key)){
			PageletBeanDefinition prePageletBean = getPageletBean(key);
			String message = "There are repeat key for different pagelet:"+
					pBeanDefinition.getPageletClass()+"."+pBeanDefinition.getMethodName()+"() and "+
					prePageletBean.getPageletClass()+"."+prePageletBean.getMethodName()+"() have same key named "+key;
			logger.debug(message);
			throw new PageletBeanDefinitionRegistryException(message);
		}
		return 	addPageletBeanDefinition(pBeanDefinition.getKey(),pBeanDefinition);
	}

	@Override
	public boolean registryPageletBeanDefinition(List<PageletBeanDefinition> pBeanDefinitions) {
		boolean flag = false;
		for(PageletBeanDefinition pbDefinition : pBeanDefinitions){
			flag = registryPageletBeanDefinition(pbDefinition);
			if(flag == false){
				String message = "Registry Error: "+pbDefinition.getKey()+" can't registry due to there is same key for other pagelet";
				logger.debug(message);
				throw new PageletBeanDefinitionRegistryException(message);
			}
		}
		return flag;
	}
	
	@Override
	public PageletBean getPageletBean(String key, BigPipeBean bigPipeBean) {
		return getPageletBean(key,bigPipeBean.getFieldParams());
	}
	
	
	@Override
	public PageletBean getPageletBean(String key, Map<String, Object> fieldValuesMap) {
		if(!isExistKey(key))
			return null;
		PageletBeanDefinition pageletBeanDefinition = this.getPageletBean(key);
		Class<?> objectType = pageletBeanDefinition.getPageletClass();
		String methodName = pageletBeanDefinition.getMethodName();
		Class<?>[] methodParamTypes = pageletBeanDefinition.getMethodParamTypes();
		Priority priority = pageletBeanDefinition.getPriority();
		Object object = null;
		Method method = null;
		try {
			object = objectType.newInstance();
			method = objectType.getMethod(methodName, methodParamTypes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e);
			throw new RuntimeException("Fail to get pagelet bean which key is "+pageletBeanDefinition.getKey());
		}	
		InjectUtils.injectFieldValueForPageletObject(object,fieldValuesMap);
		PageletBean pageletBean = new PageletBean(key,object,method,priority);
		//BigPipeBean pageletBean = new BigPipeBean(pageletBD);
		return pageletBean;
	}
	
	/**
	 * 增加一个PageletBean 
	 * @param key
	 * @param pBeanDefinition
	 * @return
	 */
	protected boolean addPageletBeanDefinition(String key,PageletBeanDefinition pBeanDefinition){
		if(isExistKey(pBeanDefinition.getKey())){
			return false;
		}
		pageletBeanMap.put(key, pBeanDefinition);
		return true;
	}
	/**
	 * 获取与key对应的PageletBeanDefinition
	 * @param key
	 * @return
	 */
	protected PageletBeanDefinition getPageletBean(String key){
		return pageletBeanMap.get(key);
	}
}
