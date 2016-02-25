package org.opensjp.openbigpipe.factory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.annotation.Pagelet;
import org.opensjp.openbigpipe.annotation.PageletSet;
import org.opensjp.openbigpipe.annotation.Priority;
import org.opensjp.openbigpipe.beans.PageletBeanDefinition;
import org.opensjp.openbigpipe.core.PageletBeanDefinitionReader;
import org.opensjp.openbigpipe.core.PageletBeanDefinitionParser;
import org.opensjp.openbigpipe.exception.InitPluginException;
import org.opensjp.openbigpipe.utils.PageletUtils;
import org.opensjp.openbigpipe.utils.StringUtils;

/**
 * 默认的PageletBean工厂，负责寻找和解析指定路径的PageletBeanDefinition，并解析和注册.
 * @author John Zheng
 *
 */
public class DefalutPageletBeanFactory extends ListablePageletBeanFactory 
implements PageletBeanDefinitionParser,PageletBeanDefinitionReader{
	private static Logger  logger = Logger.getLogger(DefalutPageletBeanFactory.class);
	/** 运行操作系统的文件分隔符 **/
	private String separator = System.getProperty("file.separator"); 
	/**
	 * 创建一个PageletBean　工厂
	 * @param packagePath
	 */
	public DefalutPageletBeanFactory(String packagePath){
		doLoadBeanDefinition(packagePath);
	}
	
	/**
	 * 负责查找packagePath的PageletBeanDefinition,解析及注册
	 * 1.负责查找packagePath 路径下的所有带@Pagelet　和@PageletSet注解的类，
	 * 2.对这些bean进行解析
	 * 3.最后注册到插件容器中
	 * @param packagePath 指定包的路径
	 */
	private void doLoadBeanDefinition(String packagePath){
		//find Class with annotation of @Pagelet or @PageletSet
		List<Class<?>> pageletClasses = this.findPageletClasses(packagePath);
		if(pageletClasses != null){
			for(Class<?> classType : pageletClasses){
				//parse  PageletBeanDefinitions
				List<PageletBeanDefinition> beanDefinitions=this.parsePageletBeanDefinition(classType);
				if(beanDefinitions != null){
					this.registryPageletBeanDefinition(beanDefinitions);
				}
			}
		}
	}
	/*
	 *=========================================================
	 *
	 *	实现注解类的寻找　
	 *
	 *========================================================= 
	 */
	@Override
	public List<Class<?>> findPageletClasses() {
		return findPageletClasses("");
	}

	@Override
	public List<Class<?>> findPageletClasses(String packageName) {
		if(StringUtils.isNull(packageName))
			return null;
		List<String> allClassNames = findAllClassNames(packageName);
		List<Class<?>> allClasses=null;
		if(allClassNames != null){
			allClasses=new ArrayList<Class<?>>();
			for(String className : allClassNames)
			{
				//忽略内部类
				try {
					Class<?> classz = Class.forName(className);
					PageletSet pageletSet = classz.getAnnotation(PageletSet.class);
					if( pageletSet != null){
						allClasses.add(classz);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		return allClasses;
	}
	/**
	 * 找到该路径下的所有class类
	 * @param classPath　扫描的类根目录
	 * @return　该目录下的所有类．
	 */
	@SuppressWarnings("unused")
	private List<String> findAllClassNames(String packageName){
		if(packageName == null){
			String message = "The value of packageScan of org.opensjp.openbigpipe.core.interceptor.BigPipeAspect can't be null";
			logger.error(message);
			throw new InitPluginException(message);
		}
		String filePath =transformFilePath(packageName);
		File file = new File(filePath);
		if(file == null){
			String message = "Can't find the package named　\""+packageName+"\", which is value of package of org.opensjp.openbigpipe.core.interceptor.BigPipeAspect.";
			logger.debug(message);
			throw new InitPluginException(message);
		}
			
		
		List<String> allClassNames=getClassName(filePath,null);
		return allClassNames;
	}
	/**
	 * 将包路径改成文件路径
	 * @param classPath
	 * @return
	 */
	private String transformFilePath(String classPath){
		String rootPath = this.getClass() .getClassLoader().getResource("").getFile().toString();
		String filePath = rootPath + classPath.replace(".", separator); 
		return filePath;
	}
	/**
	 * 获取文件路径下的所有类
	 * @param filePath　　文件路径
	 * @param className　
	 * @return
	 */
	private  List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath); 
        if(!file.exists()){//文件不存在
        	String message = "Can't find the file or directory named　\""+filePath+"\"";
        	logger.debug(message);
        	throw new InitPluginException(message);
        }
        File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));  
            } else {  
                String childFilePath = childFile.getPath(); 
                //路径分隔符
               if(childFilePath.endsWith(".class")){
                	childFilePath = childFilePath.substring(childFilePath.lastIndexOf("classes"+separator) + 8, childFilePath.lastIndexOf("."));  
                	childFilePath = childFilePath.replace(separator, "."); 
                    myClassName.add(childFilePath);
                }
            }  
        }  
        return myClassName;  
	}  
	/*
	 *=========================================================
	 *
	 *	实现解析　
	 *
	 *========================================================= 
	 */
	@Override
	public List<PageletBeanDefinition> parsePageletBeanDefinition(Class<?> classType) {
		if(classType == null)
			return null;		
		List<PageletBeanDefinition> pBeanDefinitions = null;
		if(classType.getAnnotation(PageletSet.class) != null){
			pBeanDefinitions=parsePageletSet(classType);
		}			
		return pBeanDefinitions;
	}
	/**
	 * 解析 Pagelet集合，即含有＠PageletSet的类
	 * @param classType
	 * @return
	 */
	private List<PageletBeanDefinition> parsePageletSet(Class<?> classType) {
		List<PageletBeanDefinition> pBeanDefinitions = new ArrayList<PageletBeanDefinition>();
		List<Method> pageletMethods = getAllPageletMethodByPageletAnno(classType);
		for(Method pMethod : pageletMethods){
			Pagelet pagelet = pMethod.getAnnotation(Pagelet.class);
			if(PageletUtils.verlification(pagelet, classType)){//如果注解合法
				String key = pagelet.key();
				Priority priority = pagelet.priority();			
				
				if(PageletUtils.checkPageletMethod(pMethod,classType)){
					String message = "Method of pagelet is illegal. Please check "+classType.getName();
					logger.debug(message);
					//方法不合法
					throw new InitPluginException(message);
				}else{
					Class<?>[] paramsTypes = pMethod.getParameterTypes();
					PageletBeanDefinition pBDefinition = new PageletBeanDefinition(key,classType,pMethod.getName(),
							paramsTypes,priority);	
					pBeanDefinitions.add(pBDefinition);
					
				}	
			}
		}
		return pBeanDefinitions;
	}
	/**
	 * 获取所有带有＠Pagelet注解的所有方法
	 * @param classType
	 * @return
	 */
	private List<Method> getAllPageletMethodByPageletAnno(Class<?> classType) {
		return PageletUtils.getAllPageletMethodByPageletAnno(classType);
	}
	
}
