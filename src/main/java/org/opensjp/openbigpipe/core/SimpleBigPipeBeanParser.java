package org.opensjp.openbigpipe.core;

import java.lang.reflect.Method;
import java.util.Map;

import org.opensjp.openbigpipe.annotation.BigPipe;
import org.opensjp.openbigpipe.annotation.ExecuteType;
import org.opensjp.openbigpipe.beans.BigPipeBean;
import org.opensjp.openbigpipe.utils.InjectUtils;
/**
 * 默认的BigPipeBeanParser 负责解析带@BigPipe的方法和所在类，构成一个BigPipeBean对象
 * @author John Zheng
 *
 */
public class SimpleBigPipeBeanParser implements BigPipeBeanParser {

	@Override
	public BigPipeBean parseBigPipeAnno(Class<?> classType, Object action, Method bigPipeMethod) {
		BigPipe bigPipe = bigPipeMethod.getAnnotation(BigPipe.class);
		BigPipeBean bigPipeBD = null;
		if(bigPipe != null){
			ExecuteType type = bigPipe.type();
			bigPipeBD = new BigPipeBean(type);
			Map<String,Object> fieldParams = InjectUtils.getFieldAndValueWithParamAnno(classType,action);
			bigPipeBD.setFieldParams(fieldParams);			
		}	
		return bigPipeBD;
	}

}
