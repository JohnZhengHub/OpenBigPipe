package org.opensjp.openbigpipe.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用在类方法上(特指控制类的请求方法上，如struts action对应的方法上，Servlet的servlet上),
 * 表示该请求采用openBigPipe插件处理．openBigPipe插件提供了两种执行方式，通过type属性进行配置．
 * @see org.opensjp.openbigpipe.annotation.ExecuteType
 * @author　John Zheng
 *
 */
@Target(ElementType.METHOD)    
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BigPipe {
	/**
	 * 执行方式．
	 * BIGPIPE　表示采用bigPipe算法执行
	 * CONCURRNET  后台获取数据采用并行方式，按pagelet的有优先级并行获取数据和渲染页面．最后一起发送给前端
	 * 
	 * @see org.opensjp.openbigpipe.annotation.ExecuteType
	 * @return
	 */
	ExecuteType type() default ExecuteType.BIGPIPE;
}
