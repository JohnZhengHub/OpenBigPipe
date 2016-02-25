package org.opensjp.openbigpipe.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.opensjp.openbigpipe.annotation.Pagelet;
import org.opensjp.openbigpipe.view.FreemarkerViewResource;
import org.opensjp.openbigpipe.view.ViewResource;

public class PageletUtils {

	/**
	 * 验证是否合法
	 * @param pagelet
	 * @param classType
	 * @return
	 */
	public static boolean verlification(Pagelet pagelet,Class<?> classType){
		String key = pagelet.key();
		boolean flag=true;
		if(StringUtils.isEmpty(key)){
			flag = false;
			throw new RuntimeException(" there is not value for key of pagelet.key of the pagelet can't be null or \"\" ."
					+ " Error occurred in "+classType.getName());
		}
		return flag;
	}
	
	/**
	 * 获取所有带有＠Pagelet注解的所有方法
	 * @param classType
	 * @return
	 */
	public static List<Method> getAllPageletMethodByPageletAnno(Class<?> classType) {
		List<Method> pageletMethods = new ArrayList<Method>();
		Method[] methods = classType.getMethods();
		for(Method method : methods){
			if(method.getAnnotation(Pagelet.class) != null){
				pageletMethods.add(method);
			}
		}
		return pageletMethods;
	}

	/**
	 * 检查＠pagelet 注解的方法是否符合要求
	 * @param pMethod
	 * @param classType
	 * @return
	 */
	public static boolean checkPageletMethod(Method pMethod,Class<?> classType) {
		Class<?> returnType = pMethod.getReturnType();
		if(!String.class.isAssignableFrom(returnType)){
			throw new RuntimeException("The return type of funtion with @Pagelet　must is java.lang.String.Error occurred in "
					+classType.getName()+"."+pMethod.getName()+"().");
		}
		return false;
	}
}
