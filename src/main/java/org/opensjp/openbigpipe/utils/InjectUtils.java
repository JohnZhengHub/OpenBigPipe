package org.opensjp.openbigpipe.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensjp.openbigpipe.annotation.Param;

public class InjectUtils {
	/**
	 * 向对象中含有param的BigPipeController类型的域注入BigPipeController对象
	 * @param object
	 * @param value
	 * @return
	 */
	public static boolean injectBigPipeControllerＷithParamAnno(Object object,Object value){
		Class<?> objectType = object.getClass();
		Class<?> valueType = value.getClass();
		Field[] fields = objectType.getDeclaredFields();
		int count = 0;
		for(Field field : fields){
			Class<?> fieldType = field.getType();
			Param param = field.getAnnotation(Param.class);
			if(fieldType.isAssignableFrom(valueType) && param != null){
				count++;
				try {
					field.setAccessible(true);
					field.set(object, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(count >= 1){		
			return true;
		}
		return false;
	}
	/**
	 * 为pagelet对象注入属性值
	 * @param object
	 * @param fieldValues
	 * @return
	 */
	public static boolean injectFieldValueForPageletObject(Object object,Map<String,Object> fieldValuesMap){
		for(String fieldName : fieldValuesMap.keySet()) {
			Object value = fieldValuesMap.get(fieldName);
			injectFieldParamValue(object,fieldName,value);
		}		
		return true;
	}
	
	/**
	 * 向某个类的指定属性注入值
	 * @param object
	 * @param fieldName
	 * @param fieldClassType
	 */
	public static boolean injectFieldParamValue(Object object, String fieldName,Object value){
		Class<?> objectType = object.getClass();
		if(value == null )
			return true;
		
		Class<?> valueType = value.getClass();
		try {
			Field field = objectType.getDeclaredField(fieldName);
			Class<?> fieldType = field.getType();
			Param param = field.getAnnotation(Param.class);			
			if(field != null && fieldType.isAssignableFrom(valueType) && param != null){
				field.setAccessible(true);
				field.set(object, value);
				return true;
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 向某个类的指定属性注入值
	 * @param object
	 * @param fieldName
	 * @param fieldClassType
	 */
	public static boolean injectFieldParamValueWithAnno(Object object, String fieldName,Object value){
		Class<?> objectType = object.getClass();
		Class<?> valueType = value.getClass();
		try {
			Field field = objectType.getDeclaredField(fieldName);
			Class<?> fieldType = field.getType();
			Param param = field.getAnnotation(Param.class);
			if(field != null && fieldType.isAssignableFrom(valueType) && param != null){
				field.setAccessible(true);
				field.set(object, value);
				return true;
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析类的@Param注解，并获取值．
	 * @param o
	 * @return
	 */
    public static Map<String, Object> getFieldValueWithAnnoParamFromObject(Object o) {
        Map<String, Object> extMap = new HashMap<String, Object>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Param.class) != null) {
                extMap.put(field.getName(), getFieldValue(o, field));
            }
        }
        return extMap;
    }

    

    private static Object getFieldValue(Object o, Field field) {
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            return null;
            //becase has set field accessible so do not throw this exception
        }
    }
    /**
	 * 获取所有带＠param注解的属性和属性值
	 * @param classType
	 * @param object
	 * @return
	 */
	public static Map<String, Object> getFieldAndValueWithParamAnno(Class<?> classType, Object object) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        Field[] fields =classType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Param.class) != null) {
            	paramsMap.put(field.getName(),getFieldValue(object, field));
            }
        }
        return paramsMap;
	 }
   
}