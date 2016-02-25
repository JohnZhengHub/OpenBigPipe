package org.opensjp.openbigpipe.core;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.extention.PageletExtendtionHolder;
import org.opensjp.openbigpipe.factory.DefalutPageletBeanFactory;
import org.opensjp.openbigpipe.factory.PageletBeanFactory;
import org.opensjp.openbigpipe.view.PropertiesResource;

/**
 * 抽象的BigPipeController. 主要完成初始化工作，具体的执行方式留给子类完成.
 * @author John Zheng
 *
 */
public abstract class AbstractBigPipeController implements BigPipeController{
	private static Logger logger = Logger.getLogger(AbstractBigPipeController.class);  
	/** 对应的BigPipe定义　**/
	protected BigPipeBean bigPipeBean;
	/** 表明OpenBigPipe插件是否已经初始化成功 **/
	private static volatile boolean hasInit = false; 
	/**pageletBean　工厂**/
	protected static PageletBeanFactory pageletBeanFactory;
	
	/**
	 * 初始化插件　
	 * @param packageScan　包扫描目录
	 * @param configPath　配置文件路径
	 */
	public void initPlugin(String packageScan,String configPath){
		if(hasInit)
			return;
		doInitPlugin(packageScan,configPath);
	}
	/**
	 * 采用同步方式初始化插件．这里采用double check方法
	 */
	private synchronized  void doInitPlugin(String packageScan,String configPath) {
		if(hasInit)
			return;
		if (logger.isInfoEnabled()) {
			logger.info("Initializing openBigPipe plugin.");
		}
		pageletBeanFactory = new DefalutPageletBeanFactory(packageScan);
		
		//加载配置文件，并保存在PageletExtendtionHolder的静态域中
		PageletExtendtionHolder.loading(configPath);
			
		hasInit=true;
		if (logger.isInfoEnabled()) {
			logger.info("Successfully initialize openBigPipe plugin.");
		}
	}
	
	public void setBigPipeBean(BigPipeBean bigPipeBean) {
		this.bigPipeBean = bigPipeBean;
	}
	/**
	 * 获取BigPipe 的定义BigPipeBeanDefinition
	 */
	protected BigPipeBean getBigPipeBean(){
		return bigPipeBean;
	}
	
}
