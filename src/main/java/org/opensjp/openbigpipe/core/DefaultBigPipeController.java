package org.opensjp.openbigpipe.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.beans.PageletBean;
import org.opensjp.openbigpipe.core.concurrent.Executor;
import org.opensjp.openbigpipe.exception.BigPipeResultException;
import org.opensjp.openbigpipe.exception.NotFoundPageletBeanException;
import org.opensjp.openbigpipe.exception.ViewResourceException;
import org.opensjp.openbigpipe.factory.ExecutorFactory;
import org.opensjp.openbigpipe.factory.ViewResourceFactory;
import org.opensjp.openbigpipe.utils.InjectUtils;
import org.opensjp.openbigpipe.view.ViewResource;
/**
 * 默认的BigPipe执行器，根据请求的类型转发给不同的执行器执行． 
 * @author John Zheng
 *
 */
public class DefaultBigPipeController extends AbstractBigPipeController {
	private static Logger logger = Logger.getLogger(DefaultBigPipeController.class);  
	private Executor executor;
	@Override
	public String execute(String viewFrame, String[] pageletKeys){	
		Map<String, String> pageletMap = new HashMap<String, String>();
		for(String pageletKey : pageletKeys){
			String old = pageletMap.put(pageletKey.trim(), pageletKey.trim());
			if(old != null ){
				String message = "There are repeat Pagelet key named "+old+"．Error in"
						+ bigPipeBean.getTargetClassType()+"."
						+bigPipeBean.getTargetMethodName();	
				logger.error(message);
				throw new BigPipeResultException(message);
			}
		}
		return execute(viewFrame,pageletMap);
	}

	@Override
	public String execute(String viewFrame, Map<String, String> pageletMap){
		try {
			//准备前
			List<PageletBean> pageletBeans = prepareForBigPipeWork(viewFrame,pageletMap);
			//获取执行器，按用户指定的执行方式执行
			executor = ExecutorFactory.getExecutor(bigPipeBean.getType());
			executor.execute(bigPipeBean,pageletBeans);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 执行前准备工作
	 * 1. 验证视图路径的正确性
	 * 2.重新获取Action 对象的属性值．用于注入到PageletBean　
	 * 3. 验证根据key是否能找到每个pagelet,如果能则从工厂中生成一个PageletBean 用于后期执行. 
	 * @param viewFrame
	 * @param pageletMap
	 * @return
	 */
	private List<PageletBean> prepareForBigPipeWork(String viewFrame, Map<String, String> pageletMap) {
		//验证视图路径的正确性
		ViewResource viewFrameResource = ViewResourceFactory.newInstance(viewFrame);
		if(viewFrameResource == null){
			String message = "View resource Error:\n There are some problem in the viewpath :"+viewFrame;
			logger.debug(message);
			throw new ViewResourceException(message);
		}
			
		bigPipeBean.setViewResource(viewFrameResource);		
		//执行之后Action 中的参数发生变化，重新获取属性值．
		BigPipeBean preBPBD = bigPipeBean;
		Map<String, Object> fieldValuesMap = InjectUtils.getFieldAndValueWithParamAnno(
				preBPBD.getClass(),preBPBD.getObject());
		bigPipeBean.setFieldParams(fieldValuesMap);
		
		// 验证根据key是否能找到每个pagelet,如果能则从工厂中生成一个PageletBean 用于后期执行.
		List<PageletBean> pageletBeans = new ArrayList<PageletBean>();
		for(String divId : pageletMap.keySet()){
			String key = pageletMap.get(divId);
			PageletBean pBean = pageletBeanFactory.getPageletBean(key, bigPipeBean);
			if(pBean == null){
				String message = "Can't found the pagelet wich key is "+key +". Error occurred in "
						+bigPipeBean.getTargetClassType()+"."
						+bigPipeBean.getTargetMethodName()+"().";
				logger.debug(message);
				throw new NotFoundPageletBeanException("\n"+ message);
			}else{
				pBean.setDivId(divId);
				pageletBeans.add(pBean);
			}
		}
		return pageletBeans;
	}
	
}
